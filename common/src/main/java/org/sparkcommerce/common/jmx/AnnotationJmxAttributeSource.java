/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.jmx;

import org.springframework.jmx.export.metadata.InvalidMetadataException;
import org.springframework.jmx.export.metadata.ManagedResource;

/**
 * 
 * @author jdasari
 *
 */
public class AnnotationJmxAttributeSource extends org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource {
    
    private final String appName;
    
    public AnnotationJmxAttributeSource(String appName) {
        this.appName = appName;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ManagedResource getManagedResource(Class beanClass) throws InvalidMetadataException {
        ManagedResource resource = super.getManagedResource(beanClass);
        if (resource != null && appName != null) {
            String objectName = resource.getObjectName();
            objectName += "." + appName;
            resource.setObjectName(objectName);
        }
        return resource;
    }
    
}
