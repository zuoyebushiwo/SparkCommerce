/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.vendor.service.monitor;

import org.sparkcommerce.common.vendor.service.type.ServiceStatusType;

import java.io.Serializable;

public interface ServiceStatusDetectable<T> {

    public ServiceStatusType getServiceStatus();

    public String getServiceName();
    
    public Object process(T arg) throws Exception;

}
