/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.vendor.service.exception;


public class FulfillmentPriceHostException extends FulfillmentPriceException {

    private static final long serialVersionUID = 1L;

    public FulfillmentPriceHostException() {
        super();
    }

    public FulfillmentPriceHostException(String message, Throwable cause) {
        super(message, cause);
    }

    public FulfillmentPriceHostException(String message) {
        super(message);
    }

    public FulfillmentPriceHostException(Throwable cause) {
        super(cause);
    }

}
