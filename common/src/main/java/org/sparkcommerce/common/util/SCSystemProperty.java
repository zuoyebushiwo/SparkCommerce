/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.util;

import org.sparkcommerce.common.config.service.SystemPropertiesService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Convenience class to faciliate getting system properties
 * 
 * Note that this class is scanned as a bean to pick up the applicationContext, but the methods
 * this class provides should be invoked statically.
 * 
 * @author Anand Dasari
 */
@Service("blSCSystemProperty")
public class SCSystemProperty implements ApplicationContextAware {

    protected static ApplicationContext applicationContext;
    
    /**
     * @see SystemPropertiesService#resolveSystemProperty(String)
     */
    public static String resolveSystemProperty(String name) {
        return getSystemPropertiesService().resolveSystemProperty(name);
    }
    
    public static String resolveSystemProperty(String name, String defaultValue) {
        return getSystemPropertiesService().resolveSystemProperty(name, defaultValue);
    }

    /**
     * @see SystemPropertiesService#resolveIntSystemProperty(String)
     */
    public static int resolveIntSystemProperty(String name) {
        return getSystemPropertiesService().resolveIntSystemProperty(name);
    }
    
    public static int resolveIntSystemProperty(String name, int defaultValue) {
        return getSystemPropertiesService().resolveIntSystemProperty(name, defaultValue);
    }

    /**
     * @see SystemPropertiesService#resolveBooleanSystemProperty(String)
     */
    public static boolean resolveBooleanSystemProperty(String name) {
        return getSystemPropertiesService().resolveBooleanSystemProperty(name);
    }
    
    public static boolean resolveBooleanSystemProperty(String name, boolean defaultValue) {
        return getSystemPropertiesService().resolveBooleanSystemProperty(name, defaultValue);
    }

    /**
     * @see SystemPropertiesService#resolveLongSystemProperty(String)
     */
    public static long resolveLongSystemProperty(String name) {
        return getSystemPropertiesService().resolveLongSystemProperty(name);
    }
    
    public static long resolveLongSystemProperty(String name, long defaultValue) {
        return getSystemPropertiesService().resolveLongSystemProperty(name, defaultValue);
    }
    
    /**
     * @return the "blSystemPropertiesService" bean from the application context
     */
    protected static SystemPropertiesService getSystemPropertiesService() {
        return (SystemPropertiesService) applicationContext.getBean("blSystemPropertiesService");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SCSystemProperty.applicationContext = applicationContext;
    }

}
