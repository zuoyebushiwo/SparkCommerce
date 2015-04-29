/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.money;

import java.util.HashMap;

/**
 * 
 * @author jdasari
 *
 */
public interface CurrencyDeterminationService {

    public String getCurrencyCode(HashMap currencyConsiderations);
    
}
