/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.extensibility.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;

public class PropertyConfigurer extends PropertyPlaceholderConfigurer {

    @Override
    public void setLocation(Resource location) {
        super.setLocation(location);
    }

    @Override
    public void setLocations(Resource[] locations) {
        super.setLocations(locations);
    }

}
