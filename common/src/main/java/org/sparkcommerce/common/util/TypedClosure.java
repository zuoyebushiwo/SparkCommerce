/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.util;


/**
 * A class that provides for a typed closure that will return a the specified type value from the specified type
 * 
 * @author Anand Dasari
 *
 * @param <K> the type of the key to be returned
 * @param <V> the type of the value to generate a key for
 */
public interface TypedClosure<K, V> {
    
    public K getKey(V value);

}
