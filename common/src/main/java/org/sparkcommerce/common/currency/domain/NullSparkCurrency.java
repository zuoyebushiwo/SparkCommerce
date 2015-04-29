/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.currency.domain;

public class NullSparkCurrency implements SparkCurrency {
    private static final long serialVersionUID = 7926395625817119455L;

    @Override
    public String getCurrencyCode() {
        return null;
    }

    @Override
    public void setCurrencyCode(String code) {
        // Do nothing
    }

    @Override
    public String getFriendlyName() {
        return null;
    }

    @Override
    public void setFriendlyName(String friendlyName) {
        // Do nothing
    }

    @Override
    public boolean getDefaultFlag() {
        return false;
    }

    @Override
    public void setDefaultFlag(boolean defaultFlag) {
        // Do nothing
    }

}
