/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.i18n.dao;

import org.sparkcommerce.common.extension.ExtensionHandler;
import org.sparkcommerce.common.extension.ExtensionResultHolder;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;

import javax.persistence.EntityManager;


/**
 * @author Anand Dasari
 */
public interface TranslationDaoExtensionHandler extends ExtensionHandler {
    
    /**
     * If there is a different id that should be used for a translation lookup instead of the given entityId,
     * the handler should place the result in the {@link ExtensionResultHolder} argument.
     * 
     * @param erh
     * @param em
     * @param clazz
     * @param entityId
     * @return the status of the call to the given extension handler
     */
    public ExtensionResultStatusType overrideRequestedId(ExtensionResultHolder erh, EntityManager em, 
            Class<?> clazz, Long entityId);

}
