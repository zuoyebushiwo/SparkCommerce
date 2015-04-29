/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sparkcommerce.common.RequestDTO;
import org.sparkcommerce.common.RequestDTOImpl;
import org.sparkcommerce.common.classloader.release.ThreadLocalManager;
import org.sparkcommerce.common.currency.domain.SparkCurrency;
import org.sparkcommerce.common.extension.ExtensionManager;
import org.sparkcommerce.common.locale.domain.Locale;
import org.sparkcommerce.common.sandbox.domain.SandBox;
import org.sparkcommerce.common.site.domain.Site;
import org.sparkcommerce.common.site.domain.Theme;
import org.sparkcommerce.common.util.SCRequestUtils;
import org.sparkcommerce.common.web.exception.HaltFilterChainException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 
 * @author Anand Dasari
 * @see {@link SparkRequestFilter}
 */
@Component("blRequestProcessor")
public class SparkRequestProcessor extends AbstractSparkWebRequestProcessor {

    protected final Log LOG = LogFactory.getLog(getClass());

    private static String REQUEST_DTO_PARAM_NAME = SparkRequestFilter.REQUEST_DTO_PARAM_NAME;
    public static String REPROCESS_PARAM_NAME = "REPROCESS_SC_REQUEST";
    
    public static final String SITE_ENFORCE_PRODUCTION_WORKFLOW_KEY = "site.enforce.production.workflow.update";

    @Resource(name = "blSiteResolver")
    protected SparkSiteResolver siteResolver;

    @Resource(name = "blLocaleResolver")
    protected SparkLocaleResolver localeResolver;

    @Resource(name = "blCurrencyResolver")
    protected SparkCurrencyResolver currencyResolver;

    @Resource(name = "blSandBoxResolver")
    protected SparkSandBoxResolver sandboxResolver;

    @Resource(name = "blThemeResolver")
    protected SparkThemeResolver themeResolver;

    @Resource(name = "messageSource")
    protected MessageSource messageSource;

    @Resource(name = "blTimeZoneResolver")
    protected SparkTimeZoneResolver sparkTimeZoneResolver;
    
    @Value("${thymeleaf.threadLocalCleanup.enabled}")
    protected boolean thymeleafThreadLocalCleanupEnabled = true;

    @Value("${" + SITE_ENFORCE_PRODUCTION_WORKFLOW_KEY + ":false}")
    protected boolean enforceSiteProductionWorkflowUpdate = false;
    
    @Resource(name="blEntityExtensionManagers")
    protected Map<String, ExtensionManager> entityExtensionManagers;
    
    @Override
    public void process(WebRequest request) {
        SparkRequestContext brc = new SparkRequestContext();
        brc.getAdditionalProperties().putAll(entityExtensionManagers);
        
        Site site = siteResolver.resolveSite(request);
        
        brc.setSite(site);
        brc.setWebRequest(request);
        if (site == null) {
            brc.setIgnoreSite(true);
        }
        brc.setAdmin(false);

        if (enforceSiteProductionWorkflowUpdate) {
            brc.getAdditionalProperties().put(SITE_ENFORCE_PRODUCTION_WORKFLOW_KEY, enforceSiteProductionWorkflowUpdate);
        }

        SparkRequestContext.setSparkRequestContext(brc);

        Locale locale = localeResolver.resolveLocale(request);
        TimeZone timeZone = sparkTimeZoneResolver.resolveTimeZone(request);
        SparkCurrency currency = currencyResolver.resolveCurrency(request);
        // Assumes SparkProcess
        RequestDTO requestDTO = (RequestDTO) request.getAttribute(REQUEST_DTO_PARAM_NAME, WebRequest.SCOPE_REQUEST);
        if (requestDTO == null) {
            requestDTO = new RequestDTOImpl(request);
        }

        SandBox currentSandbox = sandboxResolver.resolveSandBox(request, site);
        
        // When a user elects to switch his sandbox, we want to invalidate the current session. We'll then redirect the 
        // user to the current URL so that the configured filters trigger again appropriately.
        Boolean reprocessRequest = (Boolean) request.getAttribute(SparkRequestProcessor.REPROCESS_PARAM_NAME, WebRequest.SCOPE_REQUEST);
        if (reprocessRequest != null && reprocessRequest) {
            LOG.debug("Reprocessing request");
            if (request instanceof ServletWebRequest) {
                HttpServletRequest hsr = ((ServletWebRequest) request).getRequest();
                
                clearSparkSessionAttrs(request);
                
                StringBuffer url = hsr.getRequestURL();
                if (hsr.getQueryString() != null) {
                    url.append('?').append(hsr.getQueryString());
                }
                try {
                    ((ServletWebRequest) request).getResponse().sendRedirect(url.toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                throw new HaltFilterChainException("Reprocess required, redirecting user");
            }
        }
        
        
        if (currentSandbox != null) {
            SandBoxContext previewSandBoxContext = new SandBoxContext();
            previewSandBoxContext.setSandBoxId(currentSandbox.getId());
            previewSandBoxContext.setPreviewMode(true);
            SandBoxContext.setSandBoxContext(previewSandBoxContext);
        }
        brc.setLocale(locale);
        brc.setSparkCurrency(currency);
        brc.setSandBox(currentSandbox);

        // Note that this must happen after the request context is set up as resolving a theme is dependent on site
        Theme theme = themeResolver.resolveTheme(request);
        brc.setTheme(theme);

        brc.setMessageSource(messageSource);
        brc.setTimeZone(timeZone);
        brc.setRequestDTO(requestDTO);
        Map<String, Object> ruleMap = (Map<String, Object>) request.getAttribute("blRuleMap", WebRequest.SCOPE_REQUEST);
        if (ruleMap == null) {
            LOG.trace("Creating ruleMap and adding in Locale.");
            ruleMap = new HashMap<String, Object>();
            request.setAttribute("blRuleMap", ruleMap, WebRequest.SCOPE_REQUEST);
        } else {
            LOG.trace("Using pre-existing ruleMap - added by non standard SC process.");
        }
        ruleMap.put("locale", locale);

        String adminUserId = request.getParameter(SparkRequestFilter.ADMIN_USER_ID_PARAM_NAME);
        if (StringUtils.isNotBlank(adminUserId)) {
            //TODO: Add token logic to secure the admin user id
            brc.setAdminUserId(Long.parseLong(adminUserId));
        }

    }

    @Override
    public void postProcess(WebRequest request) {
        ThreadLocalManager.remove();
    }
    
    protected void clearSparkSessionAttrs(WebRequest request) {
        if (SCRequestUtils.isOKtoUseSession(request)) {
            request.removeAttribute(SparkLocaleResolverImpl.LOCALE_VAR, WebRequest.SCOPE_GLOBAL_SESSION);
            request.removeAttribute(SparkCurrencyResolverImpl.CURRENCY_VAR, WebRequest.SCOPE_GLOBAL_SESSION);
            request.removeAttribute(SparkTimeZoneResolverImpl.TIMEZONE_VAR, WebRequest.SCOPE_GLOBAL_SESSION);
            request.removeAttribute(SparkSandBoxResolver.SANDBOX_ID_VAR, WebRequest.SCOPE_GLOBAL_SESSION);

            // From CustomerStateRequestProcessorImpl, using explicit String because it's out of module
            request.removeAttribute("_blc_anonymousCustomer", WebRequest.SCOPE_GLOBAL_SESSION);
            request.removeAttribute("_blc_anonymousCustomerId", WebRequest.SCOPE_GLOBAL_SESSION);
        }
    }
}
