/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.vendor.service.cache;

import net.sf.ehcache.Cache;


/**
 * @author adasari
 *
 */
public interface ServiceResponseCacheable {
    
    public void clearCache();
    
    public Cache getCache();

}
