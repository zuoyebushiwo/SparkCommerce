/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web.resource;

import org.sparkcommerce.common.extension.AbstractExtensionHandler;
import org.sparkcommerce.common.extension.ExtensionResultHolder;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;


/**
 * @author Anand Dasari
 */
public class AbstractResourceRequestExtensionHandler extends AbstractExtensionHandler 
        implements ResourceRequestExtensionHandler {

    @Override
    public ExtensionResultStatusType getOverrideResource(String path, ExtensionResultHolder erh) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
