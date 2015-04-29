/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.site.service;

import org.sparkcommerce.common.extension.AbstractExtensionHandler;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;
import org.sparkcommerce.common.site.domain.Site;

/**
 * Default implementation of {@link SiteServiceExtensionHandler}
 * 
 * @author Phillip Verheyden (phillipuniverse)
 */
public class AbstractSiteServiceExtensionHandler extends AbstractExtensionHandler implements SiteServiceExtensionHandler {

    @Override
    public ExtensionResultStatusType contributeNonPersitentSiteProperties(Site from, Site to) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
