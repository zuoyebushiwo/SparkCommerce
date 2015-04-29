/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.extensibility.context.merge;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * This {@link MergeBeanStatusProvider} can be utilized by modules that are trying to provide functionality that 
 * is only required when MultiTenant is loaded.
 * 
 * @author Anand Dasari
 */
@Component("blMultiTenantMergeBeanStatusProvider")
public class MultiTenantMergeBeanStatusProvider implements MergeBeanStatusProvider {

    @Override
    public boolean isProcessingEnabled(Object bean, String beanName, ApplicationContext appCtx) {
        return appCtx.containsBean("blMultiTenantFilterClassTransformer");
    }

}
