/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web;

import org.sparkcommerce.common.extension.AbstractExtensionHandler;
import org.sparkcommerce.common.extension.ExtensionResultHolder;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;


/**
 * @author Anand Dasari
 */
public class AbstractSparkThymeleafViewResolverExtensionHandler extends AbstractExtensionHandler 
        implements SparkThymeleafViewResolverExtensionHandler {
    
    @Override
    public ExtensionResultStatusType overrideView(ExtensionResultHolder erh) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
