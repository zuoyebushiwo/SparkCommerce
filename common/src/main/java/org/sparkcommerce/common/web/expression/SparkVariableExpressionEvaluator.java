/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web.expression;

import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.spring3.expression.SpelVariableExpressionEvaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * Provides a skeleton to register multiple {@link SparkVariableExpression} implementors.
 * 
 * @author Andre Azzolini (apazzolini)
 */
public class SparkVariableExpressionEvaluator extends SpelVariableExpressionEvaluator {
    
    @Resource(name = "blVariableExpressions")
    protected List<SparkVariableExpression> expressions = new ArrayList<SparkVariableExpression>();
    
    @Override
    protected Map<String,Object> computeAdditionalExpressionObjects(final IProcessingContext processingContext) {
        Map<String, Object> map = new HashMap<String, Object>();
        
        for (SparkVariableExpression expression : expressions) {
            if (!(expression instanceof NullSparkVariableExpression)) {
                map.put(expression.getName(), expression);
            }
        }
        
        return map;
    }

}
