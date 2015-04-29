/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web.util;

import org.sparkcommerce.common.web.resource.SparkResourceHttpRequestHandler;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.Arguments;
import org.thymeleaf.spring3.context.SpringWebContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Note that the utility methods to return a js or css request handler cannot be replaced with @Resource
 * annotations due to these beans only existing in a web application context, whereas the callers of these
 * resources may exist in both web and non-web application contexts.
 * 
 * @author Anand Dasari
 */
public class ProcessorUtils {
    
    protected static Map<String, Object> cachedBeans = new HashMap<String, Object>();
    
    /**
     * Note: See the class level comment for {@link ProcessorUtils}
     * 
     * @param arguments
     * @return the "blJsResources" bean
     */
    public static SparkResourceHttpRequestHandler getJsRequestHandler(Arguments arguments) {
        String key = "blJsResources";
        SparkResourceHttpRequestHandler reqHandler = (SparkResourceHttpRequestHandler) cachedBeans.get(key);
        if (reqHandler == null) {
            final ApplicationContext appCtx = ((SpringWebContext) arguments.getContext()).getApplicationContext();
            reqHandler = (SparkResourceHttpRequestHandler) appCtx.getBean(key);
            cachedBeans.put(key, reqHandler);
        }
        return reqHandler;
    }
    
    /**
     * Note: See the class level comment for {@link ProcessorUtils}
     * 
     * @param arguments
     * @return the "blCssResources" bean
     */
    public static SparkResourceHttpRequestHandler getCssRequestHandler(Arguments arguments) {
        String key = "blCssResources";
        SparkResourceHttpRequestHandler reqHandler = (SparkResourceHttpRequestHandler) cachedBeans.get(key);
        if (reqHandler == null) {
            final ApplicationContext appCtx = ((SpringWebContext) arguments.getContext()).getApplicationContext();
            reqHandler = (SparkResourceHttpRequestHandler) appCtx.getBean(key);
            cachedBeans.put(key, reqHandler);
        }
        return reqHandler;
    }

}
