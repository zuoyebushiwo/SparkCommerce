/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.config.service;

import org.sparkcommerce.common.extension.AbstractExtensionHandler;
import org.sparkcommerce.common.extension.ExtensionResultHolder;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;


/**
 * @author jdasari
 */
public abstract class AbstractSystemPropertyServiceExtensionHandler extends AbstractExtensionHandler implements
        SystemPropertyServiceExtensionHandler {

    @Override
    public ExtensionResultStatusType resolveProperty(String propertyName, ExtensionResultHolder resultHolder) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }
}
