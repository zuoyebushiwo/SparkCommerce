/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.money;

import org.sparkcommerce.common.classloader.release.ThreadLocalManager;

import java.util.HashMap;

public class CurrencyConversionContext {
    
    private static final ThreadLocal<CurrencyConversionService> currencyConversionService = ThreadLocalManager.createThreadLocal(CurrencyConversionService.class);

    private static final ThreadLocal<HashMap> currencyConversionContext = ThreadLocalManager.createThreadLocal(HashMap.class);

    public static HashMap getCurrencyConversionContext() {
        return CurrencyConversionContext.currencyConversionContext.get();
    }
    
    public static void setCurrencyConversionContext(HashMap currencyConsiderationContext) {
        CurrencyConversionContext.currencyConversionContext.set(currencyConsiderationContext);
    }
    
    public static CurrencyConversionService getCurrencyConversionService() {
        return CurrencyConversionContext.currencyConversionService.get();
    }
    
    public static void setCurrencyConversionService(CurrencyConversionService currencyDeterminationService) {
        CurrencyConversionContext.currencyConversionService.set(currencyDeterminationService);
    }

}
