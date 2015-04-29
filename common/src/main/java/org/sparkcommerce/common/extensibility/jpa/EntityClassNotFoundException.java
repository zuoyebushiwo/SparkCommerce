/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.extensibility.jpa;

/**
 * 
 * @author jdasari
 *
 */
public class EntityClassNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EntityClassNotFoundException() {
        super();
    }

    public EntityClassNotFoundException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public EntityClassNotFoundException(String arg0) {
        super(arg0);
    }

    public EntityClassNotFoundException(Throwable arg0) {
        super(arg0);
    }

}
