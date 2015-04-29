/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Convenient class to hold the Spring application context. Note that this holds the <i>root</i> application context
 * as opposed to an individual servlet context.
 *
 * @author Anand Dasari
 */
@Component("blApplicationContextHolder")
public class ApplicationContextHolder implements ApplicationContextAware {
    
    protected static ApplicationContext context;
    
    public static ApplicationContext getApplicationContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

}
