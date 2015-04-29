/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.vendor.service.monitor.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sparkcommerce.common.vendor.service.monitor.StatusHandler;
import org.sparkcommerce.common.vendor.service.type.ServiceStatusType;

public class LogStatusHandler implements StatusHandler {

    private static final Log LOG = LogFactory.getLog(LogStatusHandler.class);

    public void handleStatus(String serviceName, ServiceStatusType status) {
        if (status.equals(ServiceStatusType.DOWN)) {
            LOG.error(serviceName + " is reporting a status of DOWN");
        } else if (status.equals(ServiceStatusType.PAUSED)) {
            LOG.warn(serviceName + " is reporting a status of PAUSED");
        } else {
            LOG.info(serviceName + " is reporting a status of UP");
        }
    }

}
