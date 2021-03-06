/*
 * #%L
 * SparkCommerce Open Admin Platform
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
package org.sparkcommerce.openadmin.security;

import org.sparkcommerce.common.sandbox.domain.SandBox;
import org.sparkcommerce.common.sandbox.service.SandBoxService;
import org.sparkcommerce.common.web.SparkRequestContext;
import org.sparkcommerce.common.web.SandBoxContext;
import org.sparkcommerce.openadmin.server.security.domain.AdminUser;
import org.sparkcommerce.openadmin.server.security.remote.SecurityVerifier;
import org.sparkcommerce.openadmin.server.service.SandBoxMode;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Jeff Fischer
 */
@Component("blAdminSandBoxFilter")
public class AdminSandBoxFilter extends OncePerRequestFilter {

    private static final String SANDBOX_ADMIN_ID_VAR = "blAdminCurrentSandboxId";
    private static String SANDBOX_ID_VAR = "blSandboxId";

    @Resource(name="blSandBoxService")
    protected SandBoxService sandBoxService;

    @Resource(name="blAdminSecurityRemoteService")
    protected SecurityVerifier adminRemoteSecurityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // this filter is not currently wired in
        HttpSession session = request.getSession();
        AdminUser adminUser = adminRemoteSecurityService.getPersistentAdminUser();
        if (adminUser == null) {
            //clear any sandbox
            session.removeAttribute(SANDBOX_ADMIN_ID_VAR);
            SandBoxContext.setSandBoxContext(null);
        } else {
            SparkRequestContext brc = SparkRequestContext.getSparkRequestContext();
            if (brc != null) {
                brc.getAdditionalProperties().put("adminUser", adminUser);
            }

            Long overrideSandBoxId = adminUser.getOverrideSandBox() == null ? null : adminUser.getOverrideSandBox().getId();
            SandBox sandBox = sandBoxService.retrieveUserSandBox(adminUser.getId(), overrideSandBoxId, adminUser.getLogin());
            session.setAttribute(SANDBOX_ADMIN_ID_VAR, sandBox.getId());
            session.removeAttribute(SANDBOX_ID_VAR);
            AdminSandBoxContext context = new AdminSandBoxContext();
            context.setSandBoxId(sandBox.getId());
            context.setSandBoxMode(SandBoxMode.IMMEDIATE_COMMIT);
            context.setAdminUser(adminUser);
            SandBoxContext.setSandBoxContext(context);
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            SandBoxContext.setSandBoxContext(null);
        }
    }
}
