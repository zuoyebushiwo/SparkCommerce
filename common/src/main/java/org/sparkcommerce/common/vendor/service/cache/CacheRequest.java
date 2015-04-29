/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.vendor.service.cache;

import java.util.List;

/**
 * @author Anand Dasari
 *
 */
public interface CacheRequest {

    public List<CacheItemRequest> getCacheItemRequests();
    
}
