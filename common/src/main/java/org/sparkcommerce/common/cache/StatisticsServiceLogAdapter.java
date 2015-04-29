/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.cache;

/**
 * @author Elbert Bautista (elbertbautista)
 */
public interface StatisticsServiceLogAdapter {

    void activateLogging(Class clazz);

    void disableLogging(Class clazz);

}
