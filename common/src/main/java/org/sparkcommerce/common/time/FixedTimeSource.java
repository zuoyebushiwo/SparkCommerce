/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.time;

public class FixedTimeSource implements TimeSource {

    private final long timeInMillis;

    public FixedTimeSource(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    public long timeInMillis() {
        return timeInMillis;
    }
}
