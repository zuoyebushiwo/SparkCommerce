/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.vendor.service.exception;

import org.sparkcommerce.common.exception.SparkException;

public class PaymentException extends SparkException {

    private static final long serialVersionUID = 1L;

    public PaymentException() {
        super();
    }

    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentException(String message) {
        super(message);
    }

    public PaymentException(Throwable cause) {
        super(cause);
    }

}
