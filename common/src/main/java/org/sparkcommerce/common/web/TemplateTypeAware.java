/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web;

import org.sparkcommerce.common.template.TemplateType;

import javax.servlet.http.HttpServletRequest;


public interface TemplateTypeAware {

    /**
     * If a custom handler is written and it knows the eventual template name, then it should return the 
     * template name when this method is called.    This method will always be called after 
     * {@link #getSparkHandlerInternal(HttpServletRequest)} and only if the Handler was able to handle the 
     * request (e.g. it returns a non-null value from {@link #getSparkHandlerInternal(HttpServletRequest)}.  
     * 
     * Listed as expected because the HandlerMapping is making a call before the controller logic has 
     * been processed.   The controller may send the user somewhere else (e.g. an error page, etc.) in which 
     * case, the expected template won't be the actual destination.
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public abstract String getExpectedTemplateName(HttpServletRequest request);

    /**
     * If a custom handler is written and it knows the eventual template name, then it should return the 
     * TemplateType when this method is called.    This method will always be called after 
     * {@link #getSparkHandlerInternal(HttpServletRequest)} and only if the Handler was able to handle the 
     * request (e.g. it returns a non-null value from {@link #getSparkHandlerInternal(HttpServletRequest)}.  
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public abstract TemplateType getTemplateType(HttpServletRequest request);

}
