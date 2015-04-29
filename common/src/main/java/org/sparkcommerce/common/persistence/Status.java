/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.persistence;

/**
 * Interface that denotes whether or not an entity is archived. Usually, entities that implement this interface also only
 * undergo soft-deletes.
 * 
 * @author Anand Dasari
 */
public interface Status {

    public void setArchived(Character archived);

    public Character getArchived();

    public boolean isActive();

}
