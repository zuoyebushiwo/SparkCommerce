/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.value;

import java.io.Serializable;

/**
 * Describes simple classes that can be assigned a name and value
 *
 * @author Anand Dasari
 */
public interface ValueAssignable<T extends Serializable> extends Serializable {

    /**
     * The value
     *
     * @return The value
     */
    T getValue();

    /**
     * The value
     *
     * @param value The value
     */
    void setValue(T value);

    /**
     * The name
     *
     * @return The name
     */
    String getName();

    /**
     * The name
     *
     * @param name The name
     */
    void setName(String name);
}
