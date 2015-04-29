/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web;

import org.springframework.web.servlet.handler.AbstractHandlerMapping;

/**
 * Adds some convenience methods to the Spring AbstractHandlerMapping for
 * SC specific HandlerMappings.
 * 
 * Always returns null from defaultHandlerMapping 
 * 
 * @author jdasari
 */
public abstract class SCAbstractHandlerMapping extends AbstractHandlerMapping {
    protected String controllerName;

    @Override
    /**
     * This handler mapping does not provide a default handler.   This method
     * has been coded to always return null.
     */
    public Object getDefaultHandler() {
        return null;        
    }
    
    /**
     * Returns the controllerName if set or "blPageController" by default.
     * @return
     */
    public String getControllerName() {
        return controllerName;
    }

    /**
     * Sets the name of the bean to use as the Handler.  Typically the name of
     * a controller bean.
     * 
     * @param controllerName
     */
    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }
}
