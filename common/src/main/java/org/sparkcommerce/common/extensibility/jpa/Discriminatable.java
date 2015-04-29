/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.extensibility.jpa;

import java.io.Serializable;

/**
 * Marker interface for a class that is included in one way or another in the multitenant architecture. If a class
 * does not implement this interface, there is no dispensation for it across sites.
 *
 * @author Anand Dasari
 */
public interface Discriminatable extends Serializable {
}
