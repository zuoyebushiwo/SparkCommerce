/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.cache.engine;

import java.io.Serializable;

/**
 * 
 * @author jfischer
 *
 */
public interface HydratedCacheManager {

    public Object getHydratedCacheElementItem(String cacheRegion, String cacheName, Serializable elementKey, String elementItemName);

    public void addHydratedCacheElementItem(String cacheRegion, String cacheName, Serializable elementKey, String elementItemName, Object elementValue);
    
}
