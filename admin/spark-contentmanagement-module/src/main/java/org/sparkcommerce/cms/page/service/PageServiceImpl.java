/*
 * #%L
 * SparkCommerce CMS Module
 * %%
 * Copyright (C) 2009 - 2013 Spark Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.sparkcommerce.cms.page.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sparkcommerce.cms.file.service.StaticAssetService;
import org.sparkcommerce.cms.page.dao.PageDao;
import org.sparkcommerce.cms.page.domain.Page;
import org.sparkcommerce.cms.page.domain.PageField;
import org.sparkcommerce.cms.page.domain.PageItemCriteria;
import org.sparkcommerce.cms.page.domain.PageRule;
import org.sparkcommerce.cms.page.domain.PageTemplate;
import org.sparkcommerce.common.cache.CacheStatType;
import org.sparkcommerce.common.cache.StatisticsService;
import org.sparkcommerce.common.extensibility.jpa.SiteDiscriminator;
import org.sparkcommerce.common.extension.ExtensionResultHolder;
import org.sparkcommerce.common.file.service.StaticAssetPathService;
import org.sparkcommerce.common.locale.domain.Locale;
import org.sparkcommerce.common.locale.service.LocaleService;
import org.sparkcommerce.common.locale.util.LocaleUtil;
import org.sparkcommerce.common.page.dto.NullPageDTO;
import org.sparkcommerce.common.page.dto.PageDTO;
import org.sparkcommerce.common.rule.RuleProcessor;
import org.sparkcommerce.common.sandbox.domain.SandBox;
import org.sparkcommerce.common.structure.dto.ItemCriteriaDTO;
import org.sparkcommerce.common.template.TemplateOverrideExtensionManager;
import org.sparkcommerce.common.web.SparkRequestContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * Created by bpolster.
 */
@Service("blPageService")
public class PageServiceImpl implements PageService {

    protected static final Log LOG = LogFactory.getLog(PageServiceImpl.class);
    
    protected static String AND = " && ";

    @Resource(name="blPageDao")
    protected PageDao pageDao;
    
    @Resource(name="blPageRuleProcessors")
    protected List<RuleProcessor<PageDTO>> pageRuleProcessors;    

    @Resource(name="blLocaleService")
    protected LocaleService localeService;
    
    @Resource(name="blStaticAssetService")
    protected StaticAssetService staticAssetService;

    @Resource(name="blStaticAssetPathService")
    protected StaticAssetPathService staticAssetPathService;

    @Resource(name="blStatisticsService")
    protected StatisticsService statisticsService;

    @Resource(name = "blTemplateOverrideExtensionManager")
    protected TemplateOverrideExtensionManager templateOverrideManager;

    protected Cache pageCache;
    protected final PageDTO NULL_PAGE = new NullPageDTO();

    /**
     * Returns the page with the passed in id.
     *
     * @param pageId - The id of the page.
     * @return The associated page.
     */
    @Override
    public Page findPageById(Long pageId) {
        return pageDao.readPageById(pageId);
    }

    @Override
    public PageTemplate findPageTemplateById(Long id) {
        return pageDao.readPageTemplateById(id);
    }
    
    @Override
    @Transactional("blTransactionManager")
    public PageTemplate savePageTemplate(PageTemplate template) {
        return pageDao.savePageTemplate(template);
    }

    /**
     * Retrieve the page if one is available for the passed in uri.
     */
    @Override
    public PageDTO findPageByURI(Locale locale, String uri, Map<String,Object> ruleDTOs, boolean secure) {
        List<PageDTO> returnList = null;
        if (uri != null) {
            Locale languageOnlyLocale = findLanguageOnlyLocale(locale);
            SparkRequestContext context = SparkRequestContext.getSparkRequestContext();
            //store the language only locale for cache since we have to use the lowest common denominator (i.e. the cache
            //locale and the pageTemplate locale used for cache invalidation can be different countries)
            Long sandBox = context.getSandBox() == null?null:context.getSandBox().getId();
            Long site = context.getSite() == null?null:context.getSite().getId();
            String key = buildKey(sandBox, site, languageOnlyLocale, uri);
            key = key + "-" + secure;
            if (context.isProductionSandBox()) {
                returnList = getPageListFromCache(key);
            }
            if (returnList == null) {
                //TODO does this pull the right sandbox in multitenant?
                List<Page> pageList = pageDao.findPageByURI(locale, languageOnlyLocale, uri);
                returnList = buildPageDTOList(pageList, secure);
                if (context.isProductionSandBox()) {
                    Collections.sort(returnList, new BeanComparator("priority"));
                    addPageListToCache(returnList, key);
                }
            }
        }
        
        PageDTO dto = evaluatePageRules(returnList, locale, ruleDTOs);
        
        if (dto.getId() != null) {
            Page page = findPageById(dto.getId());

            ExtensionResultHolder<String> erh = new ExtensionResultHolder<String>();
            templateOverrideManager.getProxy().getOverrideTemplate(erh, page);
            
            if (StringUtils.isNotBlank(erh.getResult())) {
                dto.setTemplatePath(erh.getResult());
            }
        }
        
        return dto;
    }

    @Override
    public List<Page> readAllPages() {
        return pageDao.readAllPages();
    }

    @Override
    public List<PageTemplate> readAllPageTemplates() {
        return pageDao.readAllPageTemplates();
    }

    @Override
    public void removePageFromCache(SandBox sandBox, Page p) {
        // Remove secure and non-secure instances of the page.
        // Typically the page will be in one or the other if at all.
        removePageFromCache(buildKey(sandBox, p));
    }

    @Override
    public void removePageFromCache(String baseKey) {
        // Remove secure and non-secure instances of the page.
        // Typically the page will be in one or the other if at all.
        getPageCache().remove(baseKey+"-"+true);
        getPageCache().remove(baseKey+"-"+false);
    }

    /**
     * Converts a list of pages to a list of pageDTOs.<br>
     * Internally calls buildPageDTO(...).
     *
     * @param pageList
     * @param secure
     * @return
     */
    protected List<PageDTO> buildPageDTOList(List<Page> pageList, boolean secure) {
        List<PageDTO> dtoList = new ArrayList<PageDTO>();
        if (pageList != null) {
            for(Page page : pageList) {
                dtoList.add(buildPageDTOInternal(page, secure));
            }
        }
        return dtoList;
    }

    protected PageDTO buildPageDTOInternal(Page page, boolean secure) {
        PageDTO pageDTO = new PageDTO();
        pageDTO.setId(page.getId());
        pageDTO.setDescription(page.getDescription());
        pageDTO.setUrl(page.getFullUrl());
        pageDTO.setPriority(page.getPriority());

        if (page.getPageTemplate() != null) {
            pageDTO.setTemplatePath(page.getPageTemplate().getTemplatePath());
            if (page.getPageTemplate().getLocale() != null) {
                pageDTO.setLocaleCode(page.getPageTemplate().getLocale().getLocaleCode());
            }
        }

        String cmsPrefix = staticAssetPathService.getStaticAssetUrlPrefix();

        for (String fieldKey : page.getPageFields().keySet()) {
            PageField pf = page.getPageFields().get(fieldKey);
            String originalValue = pf.getValue();
            if (StringUtils.isNotBlank(originalValue) && StringUtils.isNotBlank(cmsPrefix) && originalValue.contains(cmsPrefix)) {
                //This may either be an ASSET_LOOKUP image path or an HTML block (with multiple <img>) or a plain STRING that contains the cmsPrefix.
                //If there is an environment prefix configured (e.g. a CDN), then we must replace the cmsPrefix with this one.
                String fldValue = staticAssetPathService.convertAllAssetPathsInContent(originalValue, secure);
                pageDTO.getPageFields().put(fieldKey, fldValue);
            } else {
                pageDTO.getPageFields().put(fieldKey, originalValue);
            }
        }

        pageDTO.setRuleExpression(buildRuleExpression(page));

        if (page.getQualifyingItemCriteria() != null && page.getQualifyingItemCriteria().size() > 0) {
            pageDTO.setItemCriteriaDTOList(buildItemCriteriaDTOList(page));
        }

        return pageDTO;
    }


    protected String buildRuleExpression(Page page) {
       StringBuffer ruleExpression = null;
       Map<String, PageRule> ruleMap = page.getPageMatchRules();
       if (ruleMap != null) {
           for (String ruleKey : ruleMap.keySet()) {
               if (ruleExpression == null) {
                   ruleExpression = new StringBuffer(ruleMap.get(ruleKey).getMatchRule());
               } else {
                   ruleExpression.append(AND);
                   ruleExpression.append(ruleMap.get(ruleKey).getMatchRule());
               }
           }
       }
       if (ruleExpression != null) {
           return ruleExpression.toString();
       } else {
           return null;
       }
    }

    protected List<ItemCriteriaDTO> buildItemCriteriaDTOList(Page page) {
        List<ItemCriteriaDTO> itemCriteriaDTOList = new ArrayList<ItemCriteriaDTO>();
        for(PageItemCriteria criteria : page.getQualifyingItemCriteria()) {
            ItemCriteriaDTO criteriaDTO = new ItemCriteriaDTO();
            criteriaDTO.setMatchRule(criteria.getMatchRule());
            criteriaDTO.setQty(criteria.getQuantity());
            itemCriteriaDTOList.add(criteriaDTO);
        }
        return itemCriteriaDTOList;
    }

    protected PageDTO evaluatePageRules(List<PageDTO> pageDTOList, Locale locale, Map<String, Object> ruleDTOs) {
        if (pageDTOList == null) {
            return NULL_PAGE;
        }

        // First check to see if we have a page that matches on the full locale.
        for (PageDTO page : pageDTOList) {
            if (locale != null && locale.getLocaleCode() != null) {
                if (locale.getLocaleCode().equals(page.getLocaleCode())) {
                    if (passesPageRules(page, ruleDTOs)) {
                        return page;
                    }
                }
            }
        }

        // Otherwise, we look for a match using just the language.
        for (PageDTO page : pageDTOList) {
            if (passesPageRules(page, ruleDTOs)) {
                return page;
            }
        }

        return NULL_PAGE;
    }

    protected boolean passesPageRules(PageDTO page, Map<String, Object> ruleDTOs) {
        if (pageRuleProcessors != null) {
            for (RuleProcessor<PageDTO> processor : pageRuleProcessors) {
                boolean matchFound = processor.checkForMatch(page, ruleDTOs);
                if (! matchFound) {
                    return false;
                }
            }
        }
        return true;
    }

    protected Locale findLanguageOnlyLocale(Locale locale) {
        if (locale != null ) {
            Locale languageOnlyLocale = localeService.findLocaleByCode(LocaleUtil.findLanguageCode(locale));
            if (languageOnlyLocale != null) {
                return languageOnlyLocale;
            }
        }
        return locale;
    }

    protected String buildKey(Long currentSandBox, Long site, Locale locale, String uri) {
        StringBuilder key = new StringBuilder(uri);
        if (locale != null) {
            key.append("-").append(locale.getLocaleCode());
        }

        if (currentSandBox != null) {
            key.append("-").append(currentSandBox);
        }

        if (site != null) {
            key.append("-").append(site);
        }

        return key.toString();
    }

    @Override
    public Cache getPageCache() {
        if (pageCache == null) {
            pageCache = CacheManager.getInstance().getCache("cmsPageCache");
        }
        return pageCache;
    }

    protected String buildKey(SandBox sandBox, Page page) {
        Long sandBoxId = sandBox==null?null:sandBox.getId();
        Long siteId = (page instanceof SiteDiscriminator)?((SiteDiscriminator) page).getSiteDiscriminator():null;
        return buildKey(sandBoxId, siteId, findLanguageOnlyLocale(page.getPageTemplate().getLocale()), page.getFullUrl());
    }

    protected void addPageListToCache(List<PageDTO> pageList, String key) {
        getPageCache().put(new Element(key, pageList));
    }

    protected List<PageDTO> getPageListFromCache(String key) {
        Element cacheElement = getPageCache().get(key);
        if (cacheElement != null && cacheElement.getValue() != null) {
            statisticsService.addCacheStat(CacheStatType.PAGE_CACHE_HIT_RATE.toString(), true);
            return (List<PageDTO>) cacheElement.getValue();
        }
        statisticsService.addCacheStat(CacheStatType.PAGE_CACHE_HIT_RATE.toString(), false);
        return null;
    }

}
