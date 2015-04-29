/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web.resource;

import org.sparkcommerce.common.extension.ExtensionManager;
import org.springframework.stereotype.Service;

@Service("blResourceRequestExtensionManager")
public class ResourceRequestExtensionManager extends ExtensionManager<ResourceRequestExtensionHandler>{

    public ResourceRequestExtensionManager() {
        super(ResourceRequestExtensionHandler.class);
    }

    /**
     * The first handler to return a handled status will win
     */
    @Override
    public boolean continueOnHandled() {
        return false;
    }

}
