/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.site.service;

import org.sparkcommerce.common.extension.ExtensionHandler;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;
import org.sparkcommerce.common.site.domain.Site;
import org.sparkcommerce.common.site.domain.SiteImpl;

/**
 * <p>
 * ExtensionHandler for methods within {@link SiteServiceImpl}
 * 
 * <p>
 * Rather than implementing this interface directly you should extend your implementation from
 * {@link AbstractSiteServiceExtensionHandler}.
 * 
 * @author Anand Dasari
 * @see {@link AbstractSiteServiceExtensionHandler}
 */
public interface SiteServiceExtensionHandler extends ExtensionHandler {

    /**
     * Invoked via {@link SiteServiceImpl#getNonPersistentSite(Site)} after the initial framework clone. If more properties
     * are dynamically weaved into {@link SiteImpl} then they should be cloned here.
     * 
     * @param from the {@link Site} being copied from, usually just looked up from the database
     * @param to the 
     * @see {@link SiteServiceImpl#getNonPersistentSite(Site)}
     */
    public ExtensionResultStatusType contributeNonPersitentSiteProperties(Site from, Site to);

}
