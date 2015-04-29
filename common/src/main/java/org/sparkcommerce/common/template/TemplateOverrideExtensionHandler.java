/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.template;

import org.sparkcommerce.common.extension.ExtensionHandler;
import org.sparkcommerce.common.extension.ExtensionResultHolder;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;

/**
 * Certain objects may have templates that resolve differently based on Spark modules. This extension handler
 * provides the abilities for modules to provide that functionality.
 * 
 * @author Anand Dasari
 */
public interface TemplateOverrideExtensionHandler extends ExtensionHandler {
    
    public ExtensionResultStatusType getOverrideTemplate(ExtensionResultHolder<String> erh, Object object);


}
