/*
 * #%L
 * SparkCommerce Framework Web
 * %%
 * Copyright (C) 2009 - 2013 Spark Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.sparkcommerce.common.web.security;

import org.sparkcommerce.common.util.SCRequestUtils;
import org.sparkcommerce.common.web.controller.SparkControllerUtility;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * If the incoming request is an ajax request, the system will add the desired redirect path to the session and
 * then redirect to the path configured for the redirectPath property.
 * 
 * It is assumed that the redirectPath will be picked up by the SparkRedirectController.
 * 
 * @author bpolster
 * 
 */
@Component("blAuthenticationSuccessRedirectStrategy")
public class SparkAuthenticationSuccessRedirectStrategy implements RedirectStrategy {
    
    private String redirectPath="/redirect";
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        if (SparkControllerUtility.isAjaxRequest(request)) {
            if (SCRequestUtils.isOKtoUseSession(new ServletWebRequest(request))) {
                request.getSession().setAttribute("SC_REDIRECT_URL", url);
            }
            url = getRedirectPath();
        }
        redirectStrategy.sendRedirect(request, response, url);
    }

    public String updateLoginErrorUrlForAjax(String url) {
        String blcAjax = SparkControllerUtility.SC_AJAX_PARAMETER;
        if (url != null && url.indexOf("?") > 0) {
            url = url + "&" + blcAjax + "=true";
        } else {
            url = url + "?" + blcAjax + "=true";
        }
        return url;
    }

    public String getRedirectPath() {
        return redirectPath;
    }

    public void setRedirectPath(String redirectPath) {
        this.redirectPath = redirectPath;
    }

    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
}
