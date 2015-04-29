/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.vendor.service.monitor;

import org.sparkcommerce.common.vendor.service.type.ServiceStatusType;

public interface StatusHandler {

    public void handleStatus(String serviceName, ServiceStatusType status);

}
