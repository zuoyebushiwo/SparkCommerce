/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web;

import org.sparkcommerce.common.exception.SiteNotFoundException;
import org.sparkcommerce.common.site.domain.Site;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Returns null for the Site (typical for non-multi-site implementations of
 * Spark Commerce.
 *
 * @author bpolster
 */
public class NullSparkSiteResolver implements SparkSiteResolver {

    @Override
    public Site resolveSite(HttpServletRequest request) {
        return resolveSite(new ServletWebRequest(request));
    }
    
    @Override
    public Site resolveSite(WebRequest request) {
        return resolveSite(request, false);
    }

    @Override
    public Site resolveSite(WebRequest request, boolean allowNullSite) throws SiteNotFoundException {
        return null;
    }
    
}
