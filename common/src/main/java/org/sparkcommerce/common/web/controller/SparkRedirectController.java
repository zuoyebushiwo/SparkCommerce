/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web.controller;

import org.sparkcommerce.common.util.SCRequestUtils;
import org.springframework.ui.Model;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This controller works in conjunction with the spark-ajax style redirect.
 * 
 * The logic is quite complex but solves a problem related to redirects and
 * an Ajax form.
 * 
 * It is intended to solve a problem with using an Ajax style login modal 
 * along with Spring Security.
 * 
 * Spring Security wants to redirect after a successful login.   Unfortunately,
 * we can reliably redirect from Spring Security to a page within the SC 
 * system when the login modal is presented in Ajax.
 * 
 * To solve this problem, Spring Security can be configured to use 
 * the SparkWindowLocationRedirectStrategy.   That strategy will add an attribute to 
 * session for the page you want to redirect to if the request is coming in
 * from an Ajax call.    It will then cause a redirect that should be picked 
 * up by this controller.   This controller will then render a page with the
 * blc-redirect-div.    The client-side javaScript (SC.js) will intercept
 * this code and force the browser to load the new page (e.g. via window.location)
 * 
 * @see SparkRedirectStrategy
 * 
 * @author adasari
 */
public class SparkRedirectController {
    
    public String redirect(HttpServletRequest request, HttpServletResponse response, Model model) {
        String path = null;
        if (SCRequestUtils.isOKtoUseSession(new ServletWebRequest(request))) {
            path = (String) request.getSession().getAttribute("SC_REDIRECT_URL");
        }

        if (path == null) {
            path = request.getContextPath();
        }
        return "ajaxredirect:" + path;
    }
}
