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
public class CommaDelimitedNodeValueMerge extends NodeValueMerge {

    @Override
    public String getDelimiter() {
        return ",";
    }

    @Override
    public String getRegEx() {
        return getDelimiter();
    }
}
