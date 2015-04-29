/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.logging;

/**
 * <p>An implementation of SupportLoggerAdapter that would disable SupportLogger logging. (i.e. do nothing)</p>
 * @author Anand Dasari
 */
public class DisableSupportLoggerAdapter implements SupportLoggerAdapter {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {
        //do nothing
    }

    @Override
    public void support(String message) {
        //do nothing
    }

    @Override
    public void support(String message, Throwable t) {
        //do nothing
    }

    @Override
    public void lifecycle(LifeCycleEvent lifeCycleEvent, String message) {
        //do nothing
    }

    @Override
    public void debug(String message) {
        //do nothing
    }

    @Override
    public void debug(String message, Throwable t) {
        //do nothing
    }

    @Override
    public void error(String message) {
        //do nothing
    }

    @Override
    public void error(String message, Throwable t) {
        //do nothing
    }

    @Override
    public void fatal(String message) {
        //do nothing
    }

    @Override
    public void fatal(String message, Throwable t) {
        //do nothing
    }

    @Override
    public void info(String message) {
        //do nothing
    }

    @Override
    public void info(String message, Throwable t) {
        //do nothing
    }

    @Override
    public void warn(String message) {
        //do nothing
    }

    @Override
    public void warn(String message, Throwable t) {
        //do nothing
    }

}
