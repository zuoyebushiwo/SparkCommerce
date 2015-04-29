/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web.expression;

/**
 * A null implementation of {@link SparkVariableExpression} 
 * 
 * @author Anand Dasari
 */
public class NullSparkVariableExpression implements SparkVariableExpression {

    @Override
    public String getName() {
        return null;
    }
    
}
