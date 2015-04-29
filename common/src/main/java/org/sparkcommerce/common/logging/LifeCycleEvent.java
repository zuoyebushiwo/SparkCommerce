/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.logging;

/**
 * Enumeration describes the type of event that is being logged in the
 * SupportLogger.lifecycle method.
 *
 * @author Anand Dasari
 */
public enum LifeCycleEvent {
    START,
    END,
    TRANSFORM,
    LOADING,
    CONFIG
}
