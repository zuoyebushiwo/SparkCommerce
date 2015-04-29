/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.config.service;

import org.sparkcommerce.common.extension.ExtensionHandler;
import org.sparkcommerce.common.extension.ExtensionResultHolder;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;


/**
 * @author adasari
 */
public interface SystemPropertyServiceExtensionHandler extends ExtensionHandler {
    
    /**
     * Provides an opportunity for modules to resolve a system property.
     * 
     * @param propertyName
     * @param resultHolder
     * @return
     */
    public ExtensionResultStatusType resolveProperty(String propertyName, ExtensionResultHolder resultHolder);
    
}
