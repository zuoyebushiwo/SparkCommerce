/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.cache;

/**
 * An implementation of StatisticsServiceLogAdapter that does nothing.
 * If your application, requires a specific logging framework then the custom application can
 * implement the appropriate setLevel() methods necessary to activate and disable logging.
 *
 * @author Elbert Bautista (elbertbautista)
 */
public class NoOpStatisticsServiceLogAdapter implements StatisticsServiceLogAdapter {

    @Override
    public void activateLogging(Class clazz) {
        //do nothing
    }

    @Override
    public void disableLogging(Class clazz) {
        //do nothing
    }

}
