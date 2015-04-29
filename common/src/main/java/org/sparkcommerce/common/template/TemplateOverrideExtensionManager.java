/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.template;

import org.sparkcommerce.common.extension.ExtensionManager;
import org.springframework.stereotype.Service;

/**
 * @author Anand Dasari
 */
@Service("blTemplateOverrideExtensionManager")
public class TemplateOverrideExtensionManager extends ExtensionManager<TemplateOverrideExtensionHandler> {
    
    public TemplateOverrideExtensionManager() {
        super(TemplateOverrideExtensionHandler.class);
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
