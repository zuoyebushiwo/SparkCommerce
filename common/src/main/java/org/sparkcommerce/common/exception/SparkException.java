/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.exception;

/**
 * Base exception class for SparkExceptions that understands root cause messages.
 * 
 * @author jdasari
 */
public abstract class SparkException extends Exception implements RootCauseAccessor {

    private Throwable rootCause;

    public SparkException() {
        super();
    }

    public SparkException(String message, Throwable cause) {
        super(message, cause);
        if (cause != null) {
            rootCause = findRootCause(cause);
        } else {
            rootCause = this;
        }
    }

    private Throwable findRootCause(Throwable cause) {
        Throwable rootCause = cause;
        while (rootCause != null && rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }

    public SparkException(String message) {
        super(message);
        this.rootCause = this;

    }

    public SparkException(Throwable cause) {
        super(cause);
        if (cause != null) {
            rootCause = findRootCause(cause);
        }
    }

    public Throwable getRootCause() {
        return rootCause;
    }

    public String getRootCauseMessage() {
        if (rootCause != null) {
            return rootCause.getMessage();
        } else {
            return getMessage();
        }
    }

}
