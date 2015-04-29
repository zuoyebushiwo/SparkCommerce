/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.extensibility.jpa.convert;

import javax.persistence.spi.ClassTransformer;
import java.util.Properties;

/**
 * 
 * @author Anand Dasari
 *
 */
public interface SparkClassTransformer extends ClassTransformer {

    public void compileJPAProperties(Properties props, Object key) throws Exception;
        
}
