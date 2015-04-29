/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.i18n.dao;

import org.sparkcommerce.common.extension.AbstractExtensionHandler;
import org.sparkcommerce.common.extension.ExtensionResultHolder;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;

import javax.persistence.EntityManager;


/**
 * 
 * @author Anand Dasari
 */
public abstract class AbstractTranslationDaoExtensionHandler extends AbstractExtensionHandler implements
        TranslationDaoExtensionHandler {

    @Override
    public ExtensionResultStatusType overrideRequestedId(ExtensionResultHolder erh, EntityManager em,
            Class<?> Clazz, Long entityId) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }
    
}
