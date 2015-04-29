/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web;

import org.sparkcommerce.common.extension.ExtensionManager;
import org.springframework.stereotype.Service;


/**
 * @author Anand DAsari
 */
@Service("blSparkThymeleafViewResolverExtensionManager")
public class SparkThymeleafViewResolverExtensionManager extends ExtensionManager<SparkThymeleafViewResolverExtensionHandler> {

    public SparkThymeleafViewResolverExtensionManager() {
        super(SparkThymeleafViewResolverExtensionHandler.class);
    }

    /**
     * By default, this manager will allow other handlers to process the method when a handler returns
     * HANDLED.
     */
    @Override
    public boolean continueOnHandled() {
        return true;
    }
}
