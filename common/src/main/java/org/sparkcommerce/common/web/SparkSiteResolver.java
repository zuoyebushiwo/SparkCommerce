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
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Responsible for returning the site used by Spark Commerce for the current request.
 * For a single site installation, this will typically return null.
 *
 * @author adasari
 */
public interface SparkSiteResolver  {

    /**
     * 
     * @deprecated Use {@link #resolveSite(WebRequest)} instead
     */
    @Deprecated
    public Site resolveSite(HttpServletRequest request) throws SiteNotFoundException;

    /**
     * @see #resolveSite(WebRequest, boolean)
     */
    public Site resolveSite(WebRequest request) throws SiteNotFoundException;

    /**
     * Resolves a site for the given WebRequest. Implementations should throw a {@link SiteNotFoundException}
     * when a site could not be resolved unless the allowNullSite parameter is set to true.
     * 
     * @param request
     * @param allowNullSite
     * @return the resolved {@link Site}
     * @throws SiteNotFoundException
     */
    public Site resolveSite(final WebRequest request, final boolean allowNullSite) throws SiteNotFoundException;
}
