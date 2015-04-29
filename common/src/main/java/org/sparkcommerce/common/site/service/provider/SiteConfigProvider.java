/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.site.service.provider;

import org.sparkcommerce.common.site.domain.Site;

import java.util.Map;

/**
 * @author Joel Dasari
 */
public interface SiteConfigProvider {

    public void configSite(Site site);
    
    public void init(Map<String, Object> map);

}
