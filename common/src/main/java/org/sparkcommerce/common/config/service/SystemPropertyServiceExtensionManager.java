/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.config.service;

import org.sparkcommerce.common.extension.ExtensionManager;
import org.springframework.stereotype.Service;


/**
 * @author adasari
 */
@Service("blSystemPropertyServiceExtensionManager")
public class SystemPropertyServiceExtensionManager extends ExtensionManager<SystemPropertyServiceExtensionHandler> {

    public SystemPropertyServiceExtensionManager() {
        super(SystemPropertyServiceExtensionHandler.class);
    }

    /**
     * The first "handler" to "handle" the request wins.
     */
    public boolean continueOnHandled() {
        return false;
    }
}
