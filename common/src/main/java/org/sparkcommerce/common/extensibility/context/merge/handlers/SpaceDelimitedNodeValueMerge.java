/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.extensibility.context.merge.handlers;

/**
 * @author adasari
 */
public class SpaceDelimitedNodeValueMerge extends NodeValueMerge {

    @Override
    public String getDelimiter() {
        return " ";
    }
}
