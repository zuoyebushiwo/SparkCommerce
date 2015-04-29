/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.site.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author Joel Dasari
 */
public interface Catalog extends Serializable {

    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    List<Site> getSites();

    void setSites(List<Site> sites);
    
}
