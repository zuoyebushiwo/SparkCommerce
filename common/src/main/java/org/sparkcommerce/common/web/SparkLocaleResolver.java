/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web;

import org.sparkcommerce.common.locale.domain.Locale;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Responsible for returning the Locale to use for the current request.
 *
 * @author Anand Dasari
 */
public interface SparkLocaleResolver  {

    /**
     * @deprecated Use {@link #resolveLocale(WebRequest)} instead
     */
    @Deprecated
    public Locale resolveLocale(HttpServletRequest request);

    public Locale resolveLocale(WebRequest request);

}
