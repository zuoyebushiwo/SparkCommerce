/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.template;

import org.sparkcommerce.common.extension.AbstractExtensionHandler;
import org.sparkcommerce.common.extension.ExtensionResultHolder;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;


/**
 * @author Adasari
 */
public abstract class AbstractTemplateOverrideExtensionHandler extends AbstractExtensionHandler implements TemplateOverrideExtensionHandler {
    
    @Override
    public ExtensionResultStatusType getOverrideTemplate(ExtensionResultHolder<String> erh, Object object) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
