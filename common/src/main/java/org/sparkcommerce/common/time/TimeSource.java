/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.time;

/**
 * Provides an abstraction from the current system time.
 * Certain aspects of Spark can be run in a mode that allows the end user to override the
 * current time.
 *
 * A convenient example of this is when previewing content.   An approver may want to view
 * the site as it would appear on a particular date or time.
 *
 * See SparkProcessURLFilter for example usage of this construct.
 */
public interface TimeSource {

    long timeInMillis();
}
