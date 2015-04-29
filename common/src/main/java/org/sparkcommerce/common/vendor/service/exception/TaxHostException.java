/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.vendor.service.exception;


public class TaxHostException extends TaxException {

    private static final long serialVersionUID = 1L;

    public TaxHostException() {
        super();
    }

    public TaxHostException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaxHostException(String message) {
        super(message);
    }

    public TaxHostException(Throwable cause) {
        super(cause);
    }

}
