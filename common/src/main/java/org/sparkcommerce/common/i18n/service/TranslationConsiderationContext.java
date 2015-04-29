/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.i18n.service;

import org.sparkcommerce.common.classloader.release.ThreadLocalManager;

/**
 * Container for ThreadLocal attributes that relate to Translation.
 * 
 * @author Anand Dasari
 */
public class TranslationConsiderationContext {

    private static final ThreadLocal<TranslationConsiderationContext> translationConsiderationContext = ThreadLocalManager.createThreadLocal(TranslationConsiderationContext.class);
    
    public static boolean hasTranslation() {
        return getTranslationConsiderationContext() != null && getTranslationConsiderationContext() && getTranslationService() != null;
    }
    
    public static Boolean getTranslationConsiderationContext() {
        Boolean val = TranslationConsiderationContext.translationConsiderationContext.get().enabled;
        return val == null ? false : val;
    }
    
    public static void setTranslationConsiderationContext(Boolean isEnabled) {
        TranslationConsiderationContext.translationConsiderationContext.get().enabled = isEnabled;
    }
    
    public static TranslationService getTranslationService() {
        return TranslationConsiderationContext.translationConsiderationContext.get().service;
    }
    
    public static void setTranslationService(TranslationService translationService) {
        TranslationConsiderationContext.translationConsiderationContext.get().service = translationService;
    }

    protected Boolean enabled = false;
    protected TranslationService service;
}
