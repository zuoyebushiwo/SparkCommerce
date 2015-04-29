/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web.resource;

import org.sparkcommerce.common.extension.ExtensionHandler;
import org.sparkcommerce.common.extension.ExtensionResultHolder;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;
import org.springframework.core.io.Resource;

/**
 * Provides extension points for dealing with requests for resources
 * 
 * @author Anand Dasari
 */
public interface ResourceRequestExtensionHandler extends ExtensionHandler {
    
    public static final String RESOURCE_ATTR = "RESOURCE_ATTR";
    
    /**
     * Populates the RESOURCE_ATTR field in the ExtensionResultHolder map with an instance of
     * {@link Resource} if there is an override resource available for the current path.
     * 
     * @param path
     * @param erh
     * @return the {@link ExtensionResultStatusType}
     */
    public ExtensionResultStatusType getOverrideResource(String path, ExtensionResultHolder erh);

}
