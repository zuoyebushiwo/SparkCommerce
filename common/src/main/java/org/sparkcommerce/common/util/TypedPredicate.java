/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.util;

import org.apache.commons.collections.Predicate;

import java.lang.reflect.ParameterizedType;


/**
 * A class that provides for a typed predicat
 * 
 * @author Anand Dasari
 *
 * @param <T> the type of object the predicate uses
 */
@SuppressWarnings("unchecked")
public abstract class TypedPredicate<T> implements Predicate {
    
    protected Class<T> clazz;
    
    public TypedPredicate() {
        clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    public boolean evaluate(Object value) {
        if (value == null || clazz.isAssignableFrom(value.getClass())) {
            return eval((T) value);
        }
        return false;
    }
    
    public abstract boolean eval(T value);

}
