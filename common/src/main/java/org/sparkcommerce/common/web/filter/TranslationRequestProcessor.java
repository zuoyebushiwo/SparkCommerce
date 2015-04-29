/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web.filter;

import org.sparkcommerce.common.i18n.service.TranslationConsiderationContext;
import org.sparkcommerce.common.i18n.service.TranslationService;
import org.sparkcommerce.common.util.SCSystemProperty;
import org.sparkcommerce.common.web.AbstractSparkWebRequestProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;

/**
 * This processor is responsible for setting up the translation context.   It is intended to be used
 * by both typical Web applications and called from a ServletFilter (such as "TranslationFilter") or 
 * from a portletFilter (such as "TranslationInterceptor")
 * 
 * @author adasari
 */
@Component("blTranslationRequestProcessor")
public class TranslationRequestProcessor extends AbstractSparkWebRequestProcessor {
    
    @Resource(name = "blTranslationService")
    protected TranslationService translationService;
    
    protected boolean getTranslationEnabled() {
        return SCSystemProperty.resolveBooleanSystemProperty("i18n.translation.enabled");
    }

    @Override
    public void process(WebRequest request) {
        TranslationConsiderationContext.setTranslationConsiderationContext(getTranslationEnabled());
        TranslationConsiderationContext.setTranslationService(translationService);
    }
}
