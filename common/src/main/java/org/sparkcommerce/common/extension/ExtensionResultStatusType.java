/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.extension;


public enum ExtensionResultStatusType {
    HANDLED, // Extension handled the result but leaves it up to the manager to decide what to do next
    HANDLED_CONTINUE, // Extension handled and recommends that the manger continue
    HANDLED_STOP, // Extension handled and recommends that the manger stop
    NOT_HANDLED // Extension did not handle the request
}
