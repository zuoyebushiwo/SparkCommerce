/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.site.service;

import org.sparkcommerce.common.extension.ExtensionManager;
import org.springframework.stereotype.Service;

/**
 * Extension manager that holds the list of {@link SiteServiceExtensionHandler}.
 * 
 * @author Anand Dasari
 */
@Service("blSiteServiceExtensionManager")
public class SiteServiceExtensionManager extends ExtensionManager<SiteServiceExtensionHandler> {

    public SiteServiceExtensionManager() {
        super(SiteServiceExtensionHandler.class);
    }

}
