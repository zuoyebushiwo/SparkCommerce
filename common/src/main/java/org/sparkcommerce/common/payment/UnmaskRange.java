/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.payment;

public class UnmaskRange {

    public static final int BEGINNINGTYPE = 0;
    public static final int ENDTYPE = 1;

    private int positionType;
    private int length;

    public UnmaskRange(int startPosition, int length) {
        this.positionType = startPosition;
        this.length = length;
    }

    public int getPositionType() {
        return positionType;
    }

    public int getLength() {
        return length;
    }

}
