/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * By default, we'll resolve the customer from the "customer" attribute on the request.
 * 
 * @author Anand Dasari
 */
@Service("blRequestCustomerResolver")
public class SparkRequestCustomerResolverImpl implements ApplicationContextAware, SparkRequestCustomerResolver {
    
    private static ApplicationContext applicationContext;

    protected static String customerRequestAttributeName = "customer";

    @Override
    public Object getCustomer(HttpServletRequest request) {
        return getCustomer(new ServletWebRequest(request));
    }
    
    @Override
    public Object getCustomer() {
        WebRequest request = SparkRequestContext.getSparkRequestContext().getWebRequest();
        return getCustomer(request);
    }

    @Override
    public Object getCustomer(WebRequest request) {
        return request.getAttribute(getCustomerRequestAttributeName(), WebRequest.SCOPE_REQUEST);
    }

    @Override
    public void setCustomer(Object customer) {
        WebRequest request = SparkRequestContext.getSparkRequestContext().getWebRequest();
        request.setAttribute(getCustomerRequestAttributeName(), customer, WebRequest.SCOPE_REQUEST);
    }

    @Override
    public String getCustomerRequestAttributeName() {
        return customerRequestAttributeName;
    }

    @Override
    public void setCustomerRequestAttributeName(String customerRequestAttributeName) {
        SparkRequestCustomerResolverImpl.customerRequestAttributeName = customerRequestAttributeName;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SparkRequestCustomerResolverImpl.applicationContext = applicationContext;
    }
    
    public static SparkRequestCustomerResolver getRequestCustomerResolver() {
        return (SparkRequestCustomerResolver) applicationContext.getBean("blRequestCustomerResolver");
    }
    
}
