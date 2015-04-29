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

/**
 * 
 * @author jdasari
 *
 */
public class CurrencyConsiderationContext {
    
    private static final ThreadLocal<CurrencyDeterminationService> currencyDeterminationService = ThreadLocalManager.createThreadLocal(CurrencyDeterminationService.class);

    private static final ThreadLocal<HashMap> currencyConsiderationContext = ThreadLocalManager.createThreadLocal(HashMap.class);

    public static HashMap getCurrencyConsiderationContext() {
        return CurrencyConsiderationContext.currencyConsiderationContext.get();
    }
    
    public static void setCurrencyConsiderationContext(HashMap currencyConsiderationContext) {
        CurrencyConsiderationContext.currencyConsiderationContext.set(currencyConsiderationContext);
    }
    
    public static CurrencyDeterminationService getCurrencyDeterminationService() {
        return CurrencyConsiderationContext.currencyDeterminationService.get();
    }
    
    public static void setCurrencyDeterminationService(CurrencyDeterminationService currencyDeterminationService) {
        CurrencyConsiderationContext.currencyDeterminationService.set(currencyDeterminationService);
    }
}
