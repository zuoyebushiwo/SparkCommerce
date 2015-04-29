/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.util;

import org.apache.commons.collections.Transformer;


/**
 * A class that provides for a typed transformer.
 * 
 * @author Anand Dasari
 * @see Transformer
 * @param <K> the type of the value that will be returned by the transformer
 */
public interface TypedTransformer<K> extends Transformer {
    
    public K transform(Object input);

}
