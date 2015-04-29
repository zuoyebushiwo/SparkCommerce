/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.currency.service;

import org.sparkcommerce.common.currency.domain.SparkCurrency;

import java.util.List;

/**
 * Author: jerryocanas
 * Date: 9/6/12
 */
public interface SparkCurrencyService {

    /**
     * Returns the default Spark currency
     * @return The default currency
     */
    public SparkCurrency findDefaultSparkCurrency();

    /**
     * Returns a Spark currency found by a code
     * @return The currency
     */
    public SparkCurrency findCurrencyByCode(String currencyCode);

    /**
     * Returns a list of all the Spark Currencies
     * @return List of currencies
     */
    public List<SparkCurrency> getAllCurrencies();

    public SparkCurrency save(SparkCurrency currency);
}
