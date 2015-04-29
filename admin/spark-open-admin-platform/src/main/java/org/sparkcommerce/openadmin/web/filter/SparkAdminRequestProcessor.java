/*
 * #%L
 * SparkCommerce Open Admin Platform
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
package org.sparkcommerce.openadmin.web.filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sparkcommerce.common.classloader.release.ThreadLocalManager;
import org.sparkcommerce.common.currency.domain.SparkCurrency;
import org.sparkcommerce.common.exception.SiteNotFoundException;
import org.sparkcommerce.common.extension.ExtensionManager;
import org.sparkcommerce.common.locale.domain.Locale;
import org.sparkcommerce.common.sandbox.domain.SandBox;
import org.sparkcommerce.common.sandbox.domain.SandBoxType;
import org.sparkcommerce.common.sandbox.service.SandBoxService;
import org.sparkcommerce.common.site.domain.Site;
import org.sparkcommerce.common.util.SCRequestUtils;
import org.sparkcommerce.common.web.AbstractSparkWebRequestProcessor;
import org.sparkcommerce.common.web.SparkCurrencyResolver;
import org.sparkcommerce.common.web.SparkLocaleResolver;
import org.sparkcommerce.common.web.SparkRequestContext;
import org.sparkcommerce.common.web.SparkSandBoxResolver;
import org.sparkcommerce.common.web.SparkSiteResolver;
import org.sparkcommerce.common.web.SparkTimeZoneResolver;
import org.sparkcommerce.openadmin.server.security.domain.AdminUser;
import org.sparkcommerce.openadmin.server.security.remote.SecurityVerifier;
import org.sparkcommerce.openadmin.server.security.service.AdminSecurityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;


/**
 * 
 * @author Phillip Verheyden
 * @see {@link org.sparkcommerce.common.web.SparkRequestFilter}
 */
@Component("blAdminRequestProcessor")
public class SparkAdminRequestProcessor extends AbstractSparkWebRequestProcessor {

    public static final String SANDBOX_REQ_PARAM = "blSandBoxId";
    
    public static final String ADMIN_ENFORCE_PRODUCTION_WORKFLOW_KEY = "admin.enforce.production.workflow.update";

    protected final Log LOG = LogFactory.getLog(getClass());

    @Resource(name = "blSiteResolver")
    protected SparkSiteResolver siteResolver;

    @Resource(name = "messageSource")
    protected MessageSource messageSource;
    
    @Resource(name = "blLocaleResolver")
    protected SparkLocaleResolver localeResolver;
    
    @Resource(name = "blAdminTimeZoneResolver")
    protected SparkTimeZoneResolver sparkTimeZoneResolver;

    @Resource(name = "blCurrencyResolver")
    protected SparkCurrencyResolver currencyResolver;

    @Resource(name = "blSandBoxService")
    protected SandBoxService sandBoxService;

    @Resource(name = "blAdminSecurityRemoteService")
    protected SecurityVerifier adminRemoteSecurityService;
    
    @Resource(name = "blAdminSecurityService")
    protected AdminSecurityService adminSecurityService;
    
    @Value("${" + ADMIN_ENFORCE_PRODUCTION_WORKFLOW_KEY + ":true}")
    protected boolean enforceProductionWorkflowUpdate = true;
    
    @Resource(name="blEntityExtensionManagers")
    protected Map<String, ExtensionManager<?>> entityExtensionManagers;

    @Override
    public void process(WebRequest request) throws SiteNotFoundException {
        SparkRequestContext brc = SparkRequestContext.getSparkRequestContext();
        if (brc == null) {
            brc = new SparkRequestContext();
            SparkRequestContext.setSparkRequestContext(brc);
        }
        
        brc.getAdditionalProperties().putAll(entityExtensionManagers);
        
        if (brc.getSite() == null) {
            Site site = siteResolver.resolveSite(request);
            brc.setSite(site);
        }
        brc.setWebRequest(request);
        brc.setIgnoreSite(brc.getSite() == null);
        brc.setAdmin(true);

        brc.getAdditionalProperties().put(ADMIN_ENFORCE_PRODUCTION_WORKFLOW_KEY, enforceProductionWorkflowUpdate);
        
        Locale locale = localeResolver.resolveLocale(request);
        brc.setLocale(locale);
        
        brc.setMessageSource(messageSource);
        
        TimeZone timeZone = sparkTimeZoneResolver.resolveTimeZone(request);
        brc.setTimeZone(timeZone);

        SparkCurrency currency = currencyResolver.resolveCurrency(request);
        brc.setSparkCurrency(currency);

        prepareSandBox(request, brc);
    }

    protected void prepareSandBox(WebRequest request, SparkRequestContext brc) {
        AdminUser adminUser = adminRemoteSecurityService.getPersistentAdminUser();
        if (adminUser == null) {
            //clear any sandbox
            if (SCRequestUtils.isOKtoUseSession(request)) {
                request.removeAttribute(SparkSandBoxResolver.SANDBOX_ID_VAR, WebRequest.SCOPE_GLOBAL_SESSION);
            }
        } else {
            SandBox sandBox = null;
            if (StringUtils.isNotBlank(request.getParameter(SANDBOX_REQ_PARAM))) {
                Long sandBoxId = Long.parseLong(request.getParameter(SANDBOX_REQ_PARAM));
                sandBox = sandBoxService.retrieveUserSandBoxForParent(adminUser.getId(), sandBoxId);
                if (sandBox == null) {
                    SandBox approvalOrUserSandBox = sandBoxService.retrieveSandBoxById(sandBoxId);
                    if (approvalOrUserSandBox.getSandBoxType().equals(SandBoxType.USER)) {
                        sandBox = approvalOrUserSandBox;
                    } else {
                        sandBox = sandBoxService.createUserSandBox(adminUser.getId(), approvalOrUserSandBox);
                    }
                }
            }

            if (sandBox == null) {
                Long previouslySetSandBoxId = null;
                if (SCRequestUtils.isOKtoUseSession(request)) {
                    previouslySetSandBoxId = (Long) request.getAttribute(SparkSandBoxResolver.SANDBOX_ID_VAR,
                        WebRequest.SCOPE_GLOBAL_SESSION);
                }
                if (previouslySetSandBoxId != null) {
                    sandBox = sandBoxService.retrieveSandBoxById(previouslySetSandBoxId);
                }
            }

            if (sandBox == null) {
                List<SandBox> defaultSandBoxes = sandBoxService.retrieveSandBoxesByType(SandBoxType.DEFAULT);
                if (defaultSandBoxes.size() > 1) {
                    throw new IllegalStateException("Only one sandbox should be configured as default");
                }

                SandBox defaultSandBox;
                if (defaultSandBoxes.size() == 1) {
                    defaultSandBox = defaultSandBoxes.get(0);
                } else {
                    defaultSandBox = sandBoxService.createDefaultSandBox();
                }

                sandBox = sandBoxService.retrieveUserSandBoxForParent(adminUser.getId(), defaultSandBox.getId());
                if (sandBox == null) {
                    sandBox = sandBoxService.createUserSandBox(adminUser.getId(), defaultSandBox);
                }
            }

            // If the user just changed sandboxes, we want to update the database record.
            Long previouslySetSandBoxId = null;
            if (SCRequestUtils.isOKtoUseSession(request)) {
                previouslySetSandBoxId = (Long) request.getAttribute(SparkSandBoxResolver.SANDBOX_ID_VAR, WebRequest.SCOPE_GLOBAL_SESSION);
            }
            if (previouslySetSandBoxId != null && !sandBox.getId().equals(previouslySetSandBoxId)) {
                adminUser.setLastUsedSandBoxId(sandBox.getId());
                adminUser = adminSecurityService.saveAdminUser(adminUser);
            }

            if (SCRequestUtils.isOKtoUseSession(request)) {
                request.setAttribute(SparkSandBoxResolver.SANDBOX_ID_VAR, sandBox.getId(), WebRequest.SCOPE_GLOBAL_SESSION);
            }
            brc.setSandBox(sandBox);
            brc.getAdditionalProperties().put("adminUser", adminUser);
        }
    }

    @Override
    public void postProcess(WebRequest request) {
        ThreadLocalManager.remove();
    }

}
