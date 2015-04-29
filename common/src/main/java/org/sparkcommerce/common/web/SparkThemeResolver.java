/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web;

import org.sparkcommerce.common.site.domain.Site;
import org.sparkcommerce.common.site.domain.Theme;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Responsible for returning the theme used by Spark Commerce for the current request.
 * For a single site installation, this should return a theme whose path and name are empty string.
 *
 * @author adasari
 */
public interface SparkThemeResolver {
    
    /**
     * 
     * @deprecated Use {@link #resolveTheme(WebRequest)} instead
     */
    @Deprecated
    public Theme resolveTheme(HttpServletRequest request, Site site);
    
    public Theme resolveTheme(WebRequest request);
}
