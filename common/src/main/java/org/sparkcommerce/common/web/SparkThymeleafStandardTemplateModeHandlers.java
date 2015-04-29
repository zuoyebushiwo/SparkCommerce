/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web;

import org.thymeleaf.templatemode.ITemplateModeHandler;
import org.thymeleaf.templatemode.StandardTemplateModeHandlers;

import java.util.HashSet;
import java.util.Set;


public class SparkThymeleafStandardTemplateModeHandlers {

    public static final Set<ITemplateModeHandler> ALL_SC_TEMPLATE_MODE_HANDLERS = new HashSet<ITemplateModeHandler>();
    
    static {
        for (ITemplateModeHandler handler : StandardTemplateModeHandlers.ALL_TEMPLATE_MODE_HANDLERS) {
            ALL_SC_TEMPLATE_MODE_HANDLERS.add(wrapHandler(handler));
        }
    }
    
    protected static ITemplateModeHandler wrapHandler(ITemplateModeHandler handler) {
        return new SparkThymeleafTemplateModeHandler(handler);        
    }

    public Set<ITemplateModeHandler> getStandardTemplateModeHandlers() {
        return ALL_SC_TEMPLATE_MODE_HANDLERS;
    }
}
