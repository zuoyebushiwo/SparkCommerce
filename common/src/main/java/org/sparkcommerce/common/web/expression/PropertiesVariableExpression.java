/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web.expression;

import org.sparkcommerce.common.config.domain.SystemProperty;
import org.sparkcommerce.common.util.SCSystemProperty;
import org.sparkcommerce.common.web.processor.ConfigVariableProcessor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * This Thymeleaf variable expression class provides access to runtime configuration properties that are configured
 * in development.properties, development-shared.properties, etc, for the current environment.
 * 
 * <p>
 * This also includes properties that have been saved/overwritten in the database via {@link SystemProperty}.
 * 
 * @author Andre Azzolini (apazzolini)
 * @see {@link ConfigVariableProcessor}
 */
public class PropertiesVariableExpression implements SparkVariableExpression {
    
    @Override
    public String getName() {
        return "props";
    }
    
    public String get(String propertyName) {
        return SCSystemProperty.resolveSystemProperty(propertyName);
    }

    public int getAsInt(String propertyName) {
        return SCSystemProperty.resolveIntSystemProperty(propertyName);
    }
    
    public boolean getAsBoolean(String propertyName) {
        return SCSystemProperty.resolveBooleanSystemProperty(propertyName); 
    }
    
    public long getAsLong(String propertyName) {
        return SCSystemProperty.resolveLongSystemProperty(propertyName); 
    }
    
    /**
     * Returns true if the <b>listGrid.forceShowIdColumns</b> system property or a <b>showIds</b> request parameter is set
     * to true. Used in the admin to show ID columns when displaying list grids.
     */
    public boolean getForceShowIdColumns() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        boolean forceShow = SCSystemProperty.resolveBooleanSystemProperty("listGrid.forceShowIdColumns");
        forceShow = forceShow || "true".equals(request.getParameter("showIds"));
        
        return forceShow;
    }
    
}
