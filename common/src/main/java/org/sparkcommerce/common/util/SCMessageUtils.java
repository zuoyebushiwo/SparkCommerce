/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.util;

import org.sparkcommerce.common.web.SparkRequestContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * Convenience class to faciliate getting internationalized messages. 
 * 
 * Note that this class is scanned as a bean to pick up the applicationContext, but the methods
 * this class provides should be invoked statically.
 * 
 * @author Anand Dasari
 */
@Service("blSCMessageUtils")
public class SCMessageUtils implements ApplicationContextAware {

    protected static ApplicationContext applicationContext;
    
    /**
     * Returns the message requested by the code with no arguments and the currently set Java Locale on 
     * the {@link SparkRequestContext} as returned by {@link SparkRequestContext#getJavaLocale()}
     * 
     * @param code
     * @return the message
     */
    public static String getMessage(String code) {
        return getMessage(code, (Object) null);
    }
    
    /**
     * Returns the message requested by the code with the specified arguments and the currently set Java Locale on 
     * the {@link SparkRequestContext} as returned by {@link SparkRequestContext#getJavaLocale()}
     * 
     * @param code
     * @return the message
     */
    public static String getMessage(String code, Object... args) {
        SparkRequestContext brc = SparkRequestContext.getSparkRequestContext();
        return getMessageSource().getMessage(code, args, brc.getJavaLocale());
    }
    
    /**
     * @return the "messageSource" bean from the application context
     */
    protected static MessageSource getMessageSource() {
        return (MessageSource) applicationContext.getBean("messageSource");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SCMessageUtils.applicationContext = applicationContext;
    }

}
