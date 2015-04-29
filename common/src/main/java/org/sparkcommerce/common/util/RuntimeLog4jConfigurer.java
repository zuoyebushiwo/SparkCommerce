/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.util;

import org.springframework.util.Log4jConfigurer;

import java.io.FileNotFoundException;

/**
 * @author Joel Dasari
 */
public class RuntimeLog4jConfigurer {

    private String log4jConfigLocation;

    public String getLog4jConfigLocation() {
        return log4jConfigLocation;
    }

    public void setLog4jConfigLocation(String log4jConfigLocation) {
        this.log4jConfigLocation = log4jConfigLocation;
        try {
            Log4jConfigurer.initLogging(log4jConfigLocation);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
