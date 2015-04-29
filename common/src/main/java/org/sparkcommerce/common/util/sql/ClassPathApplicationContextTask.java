/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.util.sql;

import org.apache.tools.ant.Task;

/**
 * 
 * @author Adasari
 *
 */
public class ClassPathApplicationContextTask extends Task {

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
