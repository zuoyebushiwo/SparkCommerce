/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.cache;

/**
 * Represents a block of work to execute during a call to
 * {@link org.sparkcommerce.common.cache.AbstractCacheMissAware#getCachedObject(Class, String, String, PersistentRetrieval, String...)}
 * should a missed cache item not be detected. Should return an instance of the cache miss item type retrieved
 * from the persistent store.
 *
 * @see org.sparkcommerce.common.cache.AbstractCacheMissAware
 * @author Jeff Fischer
 */
public interface PersistentRetrieval<T> {

    public T retrievePersistentObject();

}
