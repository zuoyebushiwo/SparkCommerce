/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web.util;

import org.sparkcommerce.common.util.SCRequestUtils;
import org.sparkcommerce.common.web.SparkRequestContext;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RepeatSubmitProtectionFilter implements Filter {

    private final Map<String, List<String>> requests = new HashMap<String, List<String>>(100);

    @Override
    public void destroy() {
        //do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean useSession = true;
        if (SparkRequestContext.getSparkRequestContext() != null
                && SparkRequestContext.getSparkRequestContext().getWebRequest() != null) {
            if (!SCRequestUtils.isOKtoUseSession(SparkRequestContext.getSparkRequestContext().getWebRequest())) {
                useSession = false;
            }
        } else if (!SCRequestUtils.isOKtoUseSession(new ServletWebRequest((HttpServletRequest) request))) {
            useSession = false;
        }

        if (useSession) {
            String sessionId;
            String requestURI;
            synchronized(requests) {
                sessionId = ((HttpServletRequest) request).getSession().getId();
                requestURI = ((HttpServletRequest) request).getRequestURI();
                if (requests.containsKey(sessionId) && requests.get(sessionId).contains(requestURI)) {
                    //we are currently already processing this request
                    ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_NO_CONTENT);
                    return;
                }
                List<String> myRequests = requests.get(sessionId);
                if (myRequests == null) {
                    myRequests = new ArrayList<String>();
                    requests.put(sessionId, myRequests);
                }
                myRequests.add(requestURI);
            }
            try {
                chain.doFilter(request, response);
            } finally {
                synchronized (requests) {
                    List<String> myRequests = requests.get(sessionId);
                    myRequests.remove(requestURI);
                    if (myRequests.isEmpty()) {
                        requests.remove(sessionId);
                    }
                }
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        //do nothing
    }

}
