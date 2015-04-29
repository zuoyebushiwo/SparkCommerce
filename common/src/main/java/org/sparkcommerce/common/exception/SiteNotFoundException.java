/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.exception;

/**
 * @author adasari
 */
public class SiteNotFoundException extends RuntimeException {

    public SiteNotFoundException() {
        //do nothing
    }

    public SiteNotFoundException(Throwable cause) {
        super(cause);
    }

    public SiteNotFoundException(String message) {
        super(message);
    }

    public SiteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
