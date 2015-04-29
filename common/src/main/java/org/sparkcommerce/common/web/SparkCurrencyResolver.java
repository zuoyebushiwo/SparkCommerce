/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web;

import org.sparkcommerce.common.currency.domain.SparkCurrency;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Anand Dasari
 * @Date: 9/4/15
 */

/**
 * Responsible for returning the currency to use for the current request.
 */
public interface SparkCurrencyResolver {

    /**
     * 
     * @deprecated use {@link #resolveCurrency(WebRequest)} instead
     */
    @Deprecated
    public SparkCurrency resolveCurrency(HttpServletRequest request);
    
    public SparkCurrency resolveCurrency(WebRequest request);

}
