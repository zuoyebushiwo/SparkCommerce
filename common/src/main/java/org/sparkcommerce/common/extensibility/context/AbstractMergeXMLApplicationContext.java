/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.extensibility.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.core.io.Resource;


/**
 * Provides common functionality to all Spark merge application contexts
 * 
 * @author Anand Dasari
 */
public abstract class AbstractMergeXMLApplicationContext extends AbstractXmlApplicationContext {

    protected Resource[] configResources;
    
    protected Resource[] getConfigResources() {
        return this.configResources;
    }
    
    public AbstractMergeXMLApplicationContext(ApplicationContext parent) {
        super(parent);
    }

}
