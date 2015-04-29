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
package org.sparkcommerce.openadmin.web.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sparkcommerce.common.exception.SiteNotFoundException;
import org.sparkcommerce.common.web.SparkWebRequestProcessor;
import org.sparkcommerce.openadmin.server.service.persistence.Persistable;
import org.sparkcommerce.openadmin.server.service.persistence.PersistenceThreadManager;
import org.sparkcommerce.openadmin.server.service.persistence.TargetModeType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Responsible for setting the necessary attributes on the SparkRequestContext
 * 
 * @author Andre Azzolini (apazzolini)
 */
@Component("blAdminRequestFilter")
public class SparkAdminRequestFilter extends AbstractSparkAdminRequestFilter {

    private final Log LOG = LogFactory.getLog(SparkAdminRequestFilter.class);

    @Resource(name = "blAdminRequestProcessor")
    protected SparkWebRequestProcessor requestProcessor;

    @Resource(name="blPersistenceThreadManager")
    protected PersistenceThreadManager persistenceThreadManager;

    @Override
    public void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws IOException, ServletException {

        if (!shouldProcessURL(request, request.getRequestURI())) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Process URL not processing URL " + request.getRequestURI());
            }
            filterChain.doFilter(request, response);
            return;
        }

        try {
            persistenceThreadManager.operation(TargetModeType.SANDBOX, new Persistable <Void, RuntimeException>() {
                @Override
                public Void execute() {
                    try {
                        requestProcessor.process(new ServletWebRequest(request, response));
                        filterChain.doFilter(request, response);
                        return null;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (SiteNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } finally {
            requestProcessor.postProcess(new ServletWebRequest(request, response));
        }
    }
}
