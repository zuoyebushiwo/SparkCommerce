/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.enumeration.dao;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.sparkcommerce.common.enumeration.domain.DataDrivenEnumeration;
import org.sparkcommerce.common.enumeration.domain.DataDrivenEnumerationValue;
import org.sparkcommerce.common.persistence.EntityConfiguration;
import org.sparkcommerce.common.util.dao.TypedQueryBuilder;
import org.springframework.stereotype.Repository;


@Repository("blDataDrivenEnumerationDao")
public class DataDrivenEnumerationDaoImpl implements DataDrivenEnumerationDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name = "blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;
    
    @Override
    public DataDrivenEnumeration readEnumByKey(String enumKey) {
        TypedQuery<DataDrivenEnumeration> query = new TypedQueryBuilder<DataDrivenEnumeration>(DataDrivenEnumeration.class, "dde")
            .addRestriction("dde.key", "=", enumKey)
            .toQuery(em);
        return query.getSingleResult();
    }
    
    @Override
    public DataDrivenEnumerationValue readEnumValueByKey(String enumKey, String enumValueKey) {
        TypedQuery<DataDrivenEnumerationValue> query = 
                new TypedQueryBuilder<DataDrivenEnumerationValue>(DataDrivenEnumerationValue.class, "ddev")
            .addRestriction("ddev.type.key", "=", enumKey)
            .addRestriction("ddev.key", "=", enumValueKey)
            .toQuery(em);
        return query.getSingleResult();
    }

}
