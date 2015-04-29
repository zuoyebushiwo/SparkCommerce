/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */

package org.sparkcommerce.common.service;

import org.sparkcommerce.common.dao.GenericEntityDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("blGenericEntityService")
public class GenericEntityServiceImpl implements GenericEntityService {
    
    @Resource(name = "blGenericEntityDao")
    protected GenericEntityDao genericEntityDao;
    
    @Override
    public Object readGenericEntity(String className, Object id) {
        Class<?> clazz = genericEntityDao.getImplClass(className);
        return genericEntityDao.readGenericEntity(clazz, id);
    }

}
