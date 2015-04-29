/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.extensibility.jpa;

import org.sparkcommerce.common.classloader.release.ThreadLocalManager;

/**
 * @author Anand Dasari
 */
public class ConfigurationOnlyState {

    private static final ThreadLocal<ConfigurationOnlyState> CONFIGURATIONONLYSTATE = ThreadLocalManager.createThreadLocal(ConfigurationOnlyState.class);

    public static ConfigurationOnlyState getState() {
        return CONFIGURATIONONLYSTATE.get();
    }

    public static void setState(ConfigurationOnlyState state) {
        CONFIGURATIONONLYSTATE.set(state);
    }

    protected boolean isConfigurationOnly;

    public boolean isConfigurationOnly() {
        return isConfigurationOnly;
    }

    public void setConfigurationOnly(boolean configurationOnly) {
        isConfigurationOnly = configurationOnly;
    }
}
