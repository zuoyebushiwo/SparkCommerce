/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.presentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Configuration items to be used in conjunction with {@link ValidationConfiguration} and used by an instace of
 * org.sparkcommerce.openadmin.server.service.persistence.validation.PropertyValidator
 * 
 * @author jdasari
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ConfigurationItem {
    
    /**
     * Item name for the error message (could also be a key to a properties file to support localization)
     */
    public static String ERROR_MESSAGE = "errorMessage";
    
    /**
     * <p>The name of the validation configuration item</p>
     * 
     * @return the config item name
     */
    String itemName();
    
    /**
     * <p>The value for the validation configuration item</p>
     * 
     * @return the config item value
     */
    String itemValue();
}
