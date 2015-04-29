/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.filter;

/**
 * @author A Dasari
 */
public class PropertyFilter extends Filter {

    protected boolean isJoinTableFilter = false;
    protected String propertyName;

    public Boolean getJoinTableFilter() {
        return isJoinTableFilter;
    }

    public void setJoinTableFilter(Boolean joinTableFilter) {
        isJoinTableFilter = joinTableFilter;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
