/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common;

/**
 * In order for the Spark Administration to display enumerated values with meaningful labels,
 * enumerations should implement this interface.
 *
 * @author Adasari
 *
 */
public interface SparkEnumerationType {

    public String getType();
    public String getFriendlyType();
    
}
