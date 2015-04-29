/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.time;

public class DefaultTimeSource implements TimeSource {

    public long timeInMillis() {
        return System.currentTimeMillis();
    }
}
