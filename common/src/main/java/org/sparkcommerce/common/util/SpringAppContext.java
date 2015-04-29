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

/**
 * Provides a means for classes that do not normally have access to
 * a servlet context or application context to be able to obtain
 * the current Spring ApplicationContext instance. This should be a last
 * resort, as it is unlikely this class is ever needed unless an
 * instance of ApplicationContext is required in a custom class
 * instantiated by third-party code.
 * 
 * @author jdasari
 *
 */
public class SpringAppContext implements ApplicationContextAware {
    
    private static ApplicationContext appContext;

    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        this.appContext = appContext;
    }

    public static ApplicationContext getApplicationContext() {
        return appContext;
    }
}
