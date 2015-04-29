/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.rule;

import java.util.Map;

public interface RuleProcessor<T> {

    public abstract boolean checkForMatch(T sc, Map<String, Object> valueMap);

}
