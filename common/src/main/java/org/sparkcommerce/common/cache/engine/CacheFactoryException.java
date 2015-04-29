/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.cache.engine;

/**
 * 
 * @author jfischer
 *
 */
public class CacheFactoryException extends Exception {

    private static final long serialVersionUID = 1L;

    public CacheFactoryException() {
        super();
    }

    public CacheFactoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheFactoryException(String message) {
        super(message);
    }

    public CacheFactoryException(Throwable cause) {
        super(cause);
    }

}
