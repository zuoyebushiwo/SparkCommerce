/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web;

import org.sparkcommerce.common.extension.ExtensionHandler;
import org.sparkcommerce.common.extension.ExtensionResultHolder;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;


/**
 * @author Anand Dasari
 */
public interface SparkThymeleafViewResolverExtensionHandler extends ExtensionHandler {
    
    public ExtensionResultStatusType overrideView(ExtensionResultHolder erh);

}
