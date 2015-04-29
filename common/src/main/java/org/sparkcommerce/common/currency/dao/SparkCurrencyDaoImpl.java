/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.currency.dao;

import org.sparkcommerce.common.currency.domain.SparkCurrency;
import org.sparkcommerce.common.persistence.EntityConfiguration;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Author: jerryocanas
 * Date: 9/6/12
 */

@Repository("blCurrencyDao")
public class SparkCurrencyDaoImpl implements SparkCurrencyDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    @Override
    public SparkCurrency findDefaultSparkCurrency() {
        Query query = em.createNamedQuery("BC_READ_DEFAULT_CURRENCY");
        query.setHint(org.hibernate.ejb.QueryHints.HINT_CACHEABLE, true);
        List<SparkCurrency> currencyList = (List<SparkCurrency>) query.getResultList();
        if (currencyList.size() >= 1) {
            return currencyList.get(0);
        }
        return null;
    }

    /**
     * @return The locale for the passed in code
     */
    @Override
    public SparkCurrency findCurrencyByCode(String currencyCode) {
        Query query = em.createNamedQuery("BC_READ_CURRENCY_BY_CODE");
        query.setParameter("currencyCode", currencyCode);
        query.setHint(org.hibernate.ejb.QueryHints.HINT_CACHEABLE, true);
        List<SparkCurrency> currencyList = (List<SparkCurrency>) query.getResultList();
        if (currencyList.size() >= 1) {
            return currencyList.get(0);
        }
        return null;
    }

    @Override
    public List<SparkCurrency> getAllCurrencies() {
        Query query = em.createNamedQuery("BC_READ_ALL_CURRENCIES");
        query.setHint(org.hibernate.ejb.QueryHints.HINT_CACHEABLE, true);
        return (List<SparkCurrency>) query.getResultList();
    }

    @Override
    public SparkCurrency save(SparkCurrency currency) {
        return em.merge(currency);
    }
}
