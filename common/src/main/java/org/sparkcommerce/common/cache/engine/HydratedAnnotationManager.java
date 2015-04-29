/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.cache.engine;

/**
 * 
 * @author jfischer
 *
 */
public interface HydratedAnnotationManager {

    public HydrationDescriptor getHydrationDescriptor(Object entity);
    
}
