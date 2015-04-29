/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.security.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sparkcommerce.common.exception.ServiceException;
import org.sparkcommerce.common.security.service.ExploitProtectionService;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Checks the validity of the CSRF token on every POST request.
 * You can inject excluded Request URI patterns to bypass this filter.
 * This filter uses the AntPathRequestMatcher which compares a pre-defined ant-style pattern against the URL
 * ({@code servletPath + pathInfo}) of an {@code HttpServletRequest}.
 * This allows you to use wildcard matching as well, for example {@code /**} or {@code **}
 *
 * @see org.springframework.security.web.util.AntPathRequestMatcher
 *
 * @author Anand Dasari
 */
public class CsrfFilter extends GenericFilterBean {
    protected static final Log LOG = LogFactory.getLog(CsrfFilter.class);
    
    @Resource(name="blExploitProtectionService")
    protected ExploitProtectionService exploitProtectionService;

    protected List<String> excludedRequestPatterns;

    @Override
    public void doFilter(ServletRequest baseRequest, ServletResponse baseResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) baseRequest;
        HttpServletResponse response = (HttpServletResponse) baseResponse;

        boolean excludedRequestFound = false;
        if (excludedRequestPatterns != null && excludedRequestPatterns.size() > 0) {
            for (String pattern : excludedRequestPatterns) {
                RequestMatcher matcher = new AntPathRequestMatcher(pattern);
                if (matcher.matches(request)){
                    excludedRequestFound = true;
                    break;
                }
            }
        }

        // We only validate CSRF tokens on POST
        if (request.getMethod().equals("POST") && !excludedRequestFound) {
            String requestToken = request.getParameter(exploitProtectionService.getCsrfTokenParameter());
            try {
                exploitProtectionService.compareToken(requestToken);
            } catch (ServiceException e) {
                throw new ServletException(e);
            }
        }
        
        chain.doFilter(request, response);
    }

    public List<String> getExcludedRequestPatterns() {
        return excludedRequestPatterns;
    }

    /**
     * This allows you to declaratively set a list of excluded Request Patterns
     *
     * <bean id="blCsrfFilter" class="org.sparkcommerce.common.security.handler.CsrfFilter" >
     *     <property name="excludedRequestPatterns">
     *         <list>
     *             <value>/exclude-me/**</value>
     *         </list>
     *     </property>
     * </bean>
     *
     **/
    public void setExcludedRequestPatterns(List<String> excludedRequestPatterns) {
        this.excludedRequestPatterns = excludedRequestPatterns;
    }
}
