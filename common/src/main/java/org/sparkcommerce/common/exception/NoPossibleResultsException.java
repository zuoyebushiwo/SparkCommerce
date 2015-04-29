/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.exception;

/**
 * The admin will throw this exception when a query is attempted across multiple class hierarchies
 * because it is impossible for such a query to produce any results.
 * 
 * @author adasari
 */
public class NoPossibleResultsException extends RuntimeException {
    
    private static final long serialVersionUID = 2422275745139590462L;

    // for serialization purposes
    protected NoPossibleResultsException() {
        super();
    }
    
    public NoPossibleResultsException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public NoPossibleResultsException(String message) {
        super(message);
    }
    
    public NoPossibleResultsException(Throwable cause) {
        super(cause);
    }

}
