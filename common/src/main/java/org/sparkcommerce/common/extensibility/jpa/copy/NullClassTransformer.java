/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.extensibility.jpa.copy;

import org.sparkcommerce.common.extensibility.jpa.convert.SparkClassTransformer;

import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Properties;

/**
 * This class transformer will do nothing. The main use case for this transformer is when you would prefer to not
 * have a module's template classes copied over, and would rather do it yourself. 
 * 
 * This transformer should not typically be used.
 * 
 * @author Adasari
 */
public class NullClassTransformer implements SparkClassTransformer {
    
    @Override
    public void compileJPAProperties(Properties props, Object key) throws Exception {
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, 
            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        return null;
    }

}

