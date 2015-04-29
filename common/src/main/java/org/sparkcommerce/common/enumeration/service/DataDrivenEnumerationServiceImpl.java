/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.enumeration.service;

import javax.annotation.Resource;

import org.sparkcommerce.common.enumeration.dao.DataDrivenEnumerationDao;
import org.sparkcommerce.common.enumeration.domain.DataDrivenEnumeration;
import org.sparkcommerce.common.enumeration.domain.DataDrivenEnumerationValue;
import org.springframework.stereotype.Service;


@Service("blDataDrivenEnumerationService")
public class DataDrivenEnumerationServiceImpl implements DataDrivenEnumerationService {

    @Resource(name = "blDataDrivenEnumerationDao")
    protected DataDrivenEnumerationDao dao;

    @Override
    public DataDrivenEnumeration findEnumByKey(String enumKey) {
        return dao.readEnumByKey(enumKey);
    }
    
    @Override
    public DataDrivenEnumerationValue findEnumValueByKey(String enumKey, String enumValueKey) {
        return dao.readEnumValueByKey(enumKey, enumValueKey);
    }

}
