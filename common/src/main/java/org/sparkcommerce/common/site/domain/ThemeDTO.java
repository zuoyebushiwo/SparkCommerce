/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.site.domain;

/**
 * Stores the path to the theme.
 * @author  :Adasari
 */
public class ThemeDTO implements Theme {

    public String path = "";
    public String name = "";
    public Long id;
    
    public ThemeDTO() {
        // empty constructor
    }
    
    public ThemeDTO(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
