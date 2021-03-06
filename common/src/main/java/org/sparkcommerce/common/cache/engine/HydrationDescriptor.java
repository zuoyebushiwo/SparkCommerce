/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.cache.engine;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 
 * @author jfischer
 *
 */
public class HydrationDescriptor {

    private Map<String, HydrationItemDescriptor> hydratedMutators;
    private Method[] idMutators;
    private String cacheRegion;
    
    public Map<String, HydrationItemDescriptor> getHydratedMutators() {
        return hydratedMutators;
    }
    
    public Method[] getIdMutators() {
        return idMutators;
    }
    
    public String getCacheRegion() {
        return cacheRegion;
    }

    public void setHydratedMutators(Map<String, HydrationItemDescriptor> hydratedMutators) {
        this.hydratedMutators = hydratedMutators;
    }

    public void setIdMutators(Method[] idMutators) {
        this.idMutators = idMutators;
    }

    public void setCacheRegion(String cacheRegion) {
        this.cacheRegion = cacheRegion;
    }
    
}
