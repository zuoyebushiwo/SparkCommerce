/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.currency.service;

import org.sparkcommerce.common.currency.dao.SparkCurrencyDao;
import org.sparkcommerce.common.currency.domain.SparkCurrency;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: jerryocanas
 * Date: 9/6/12
 */

@Service("blCurrencyService")
public class SparkCurrencyServiceImpl implements SparkCurrencyService {

    @Resource(name="blCurrencyDao")
    protected SparkCurrencyDao currencyDao;

    /**
     * Returns the default Spark currency
     * @return The default currency
     */
    @Override
    public SparkCurrency findDefaultSparkCurrency() {
        return currencyDao.findDefaultSparkCurrency();
    }

    /**
     * @return The currency for the passed in code
     */
    @Override
    public SparkCurrency findCurrencyByCode(String currencyCode) {
        return currencyDao.findCurrencyByCode(currencyCode);
    }

    /**
     * Returns a list of all the Spark Currencies
     *@return List of currencies
     */
    @Override
    public List<SparkCurrency> getAllCurrencies() {
        return currencyDao.getAllCurrencies();
    }

    @Override
    public SparkCurrency save(SparkCurrency currency) {
        return currencyDao.save(currency);
    }
}
