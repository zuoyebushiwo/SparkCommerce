/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.config.service;

import org.sparkcommerce.common.config.domain.ModuleConfiguration;

public interface ModuleProvider {

    /**
     * Indicates if, given the configuration, this module can respond to the particular request.
     * 
     * @param config
     * @return
     */
    public boolean canRespond(ModuleConfiguration config);


}
