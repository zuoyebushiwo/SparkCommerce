/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.sandbox;

/**
 * The workflow code will not persist an entity if the changes detected are limited to fields whose type is assignable
 * from this interface, and the persistence is being requested for a production record outside of a production deploy
 * step.
 *
 * @author Joel Dasari
 */
public interface SandBoxNonProductionSkip {
}
