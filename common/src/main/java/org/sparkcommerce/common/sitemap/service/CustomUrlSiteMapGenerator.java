/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */

package org.sparkcommerce.common.sitemap.service;

import org.apache.commons.lang3.StringUtils;
import org.sparkcommerce.common.file.service.SparkFileUtils;
import org.sparkcommerce.common.sitemap.domain.CustomUrlSiteMapGeneratorConfiguration;
import org.sparkcommerce.common.sitemap.domain.SiteMapGeneratorConfiguration;
import org.sparkcommerce.common.sitemap.domain.SiteMapUrlEntry;
import org.sparkcommerce.common.sitemap.service.type.SiteMapGeneratorType;
import org.sparkcommerce.common.sitemap.wrapper.SiteMapURLWrapper;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Responsible for generating site map entries.   
 * 
 * Each SiteMapGenerator can generate 
 * 
 * @author bpolster
 *
 */
@Component("blCustomSiteMapGenerator")
public class CustomUrlSiteMapGenerator implements SiteMapGenerator {
    
    /**
     * Returns true if this SiteMapGenerator is able to process the passed in siteMapGeneratorConfiguration.   
     * 
     * @param siteMapGeneratorConfiguration
     * @return
     */
    public boolean canHandleSiteMapConfiguration(SiteMapGeneratorConfiguration siteMapGeneratorConfiguration) {
        return SiteMapGeneratorType.CUSTOM.equals(siteMapGeneratorConfiguration.getSiteMapGeneratorType());
    }

    @Override
    public void addSiteMapEntries(SiteMapGeneratorConfiguration smgc, SiteMapBuilder siteMapBuilder) {
        for (SiteMapUrlEntry urlEntry : ((CustomUrlSiteMapGeneratorConfiguration) smgc).getCustomURLEntries()) {
            if (StringUtils.isEmpty(urlEntry.getLocation())) {
                continue;
            }
            SiteMapURLWrapper siteMapUrl = new SiteMapURLWrapper();

            // location
            siteMapUrl.setLoc(generateUri(siteMapBuilder, urlEntry));

            // change frequency
            if (urlEntry.getSiteMapChangeFreq() != null) {
                siteMapUrl.setChangeFreqType(urlEntry.getSiteMapChangeFreq());
            } else {
                siteMapUrl.setChangeFreqType(smgc.getSiteMapChangeFreq());
            }

            // priority
            if (urlEntry.getSiteMapPriority() != null) {
                siteMapUrl.setPriorityType(urlEntry.getSiteMapPriority());
            } else {
                siteMapUrl.setPriorityType(smgc.getSiteMapPriority());
            }

            // lastModDate
            siteMapUrl.setLastModDate(generateDate(urlEntry));
            
            siteMapBuilder.addUrl(siteMapUrl);
        }
    }
    
    protected String generateUri(SiteMapBuilder smb, SiteMapUrlEntry urlEntry) {
        String url = urlEntry.getLocation();
        if (url.contains("://")) {
            return url;
        } else {
            return SparkFileUtils.appendUnixPaths(smb.getBaseUrl(), url);
        }
    }

    protected Date generateDate(SiteMapUrlEntry urlEntry) {
        if(urlEntry.getLastMod() != null) {
            return urlEntry.getLastMod();
        } else {
            return new Date();
        }
    }

}
