/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.extensibility.context.merge.exceptions;

/**
 * This exception is thrown when a problem is encountered during
 * the MergeManager initialization
 * 
 * @author jdasari
 *
 */
public class MergeManagerSetupException extends Exception {

    private static final long serialVersionUID = 1L;
    
    public MergeManagerSetupException() {
        super();
    }

    public MergeManagerSetupException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public MergeManagerSetupException(String arg0) {
        super(arg0);
    }

    public MergeManagerSetupException(Throwable arg0) {
        super(arg0);
    }

}
