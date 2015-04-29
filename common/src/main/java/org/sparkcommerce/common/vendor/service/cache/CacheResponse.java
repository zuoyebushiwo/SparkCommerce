/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.vendor.service.cache;

/**
 * @author adasari
 *
 */
public interface CacheResponse {

    public Object[] getCacheItemResponses();
    public void setCacheItemResponses(Object[] cacheItemResponses);
    
}
