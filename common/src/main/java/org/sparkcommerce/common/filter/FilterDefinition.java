/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anand Dasari
 */
public class FilterDefinition {

    protected String name;
    protected List<FilterParameter> params = new ArrayList<FilterParameter>();
    protected String entityImplementationClassName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FilterParameter> getParams() {
        return params;
    }

    public void setParams(List<FilterParameter> params) {
        this.params = params;
    }

    public String getEntityImplementationClassName() {
        return entityImplementationClassName;
    }

    public void setEntityImplementationClassName(String entityImplementationClassName) {
        this.entityImplementationClassName = entityImplementationClassName;
    }
}
