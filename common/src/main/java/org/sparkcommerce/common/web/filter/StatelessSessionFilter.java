/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web.filter;

import org.sparkcommerce.common.util.SCRequestUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sets a request attribute that informs all Spark Filters that follow NOT to use the HTTP Session.
 * 
 * Intended for use by REST api requests.
 * 
 * @author bpolster
 */
@Component("blStatelessSessionFilter")
public class StatelessSessionFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        SCRequestUtils.setOKtoUseSession(new ServletWebRequest((HttpServletRequest) request, (HttpServletResponse) response), Boolean.FALSE);
        SessionlessHttpServletRequestWrapper wrapper = new SessionlessHttpServletRequestWrapper((HttpServletRequest) request);
        filterChain.doFilter(wrapper, response);
    }
}
