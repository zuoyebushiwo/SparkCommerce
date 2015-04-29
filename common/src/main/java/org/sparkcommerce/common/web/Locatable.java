/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */

package org.sparkcommerce.common.web;


public interface Locatable {
    
    /**
     * If this is returning something that is non-null, this <b>MUST</b> return a String that starts with a slash
     * and does not end with a slash.
     * 
     * @return the url of the locatable item
     */
    public String getLocation();

}
