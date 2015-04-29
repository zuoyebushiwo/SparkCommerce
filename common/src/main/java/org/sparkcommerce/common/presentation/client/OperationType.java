/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.presentation.client;

/**
 * Describes the target of an admin operation. This can be a regular entity itself, or a collection
 * or map property.
 *
 * @author JDasari
 *
 */
public enum OperationType {
    NONDESTRUCTIVEREMOVE,
    BASIC,
    ADORNEDTARGETLIST,
    MAP
}
