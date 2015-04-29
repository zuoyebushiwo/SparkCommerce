/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.util;

/**
 * @author Anand Dasari
 */
public interface TransactionalOperation {

    void execute() throws Throwable;

}
