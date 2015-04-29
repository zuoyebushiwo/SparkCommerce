/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sparkcommerce.common.util.SCRequestUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.TimeZone;

/**
 * Responsible for returning the timezone to use for the current request.
 *
 * @author Anand Dasari
 */
@Component("blTimeZoneResolver")
public class SparkTimeZoneResolverImpl implements SparkTimeZoneResolver {
    private final Log LOG = LogFactory.getLog(SparkTimeZoneResolverImpl.class);
    
    /**
     * Parameter/Attribute name for the current language
     */
    public static String TIMEZONE_VAR = "blTimeZone";

    /**
     * Parameter/Attribute name for the current language
     */
    public static String TIMEZONE_CODE_PARAM = "blTimeZoneCode";

    @Override
    public TimeZone resolveTimeZone(WebRequest request) {
        TimeZone timeZone = null;

        // First check for request attribute
        timeZone = (TimeZone) request.getAttribute(TIMEZONE_VAR, WebRequest.SCOPE_REQUEST);

        // Second, check for a request parameter
        if (timeZone == null && SCRequestUtils.getURLorHeaderParameter(request, TIMEZONE_CODE_PARAM) != null) {
            String timeZoneCode = SCRequestUtils.getURLorHeaderParameter(request, TIMEZONE_CODE_PARAM);
            timeZone = TimeZone.getTimeZone(timeZoneCode);

            if (LOG.isTraceEnabled()) {
                LOG.trace("Attempt to find TimeZone by param " + timeZoneCode + " resulted in " + timeZone);
            }
        }

        // Third, check the session 
        if (timeZone == null && SCRequestUtils.isOKtoUseSession(request)) {
            //@TODO verify if we should take this from global session
            timeZone = (TimeZone) request.getAttribute(TIMEZONE_VAR, WebRequest.SCOPE_GLOBAL_SESSION);
            if (LOG.isTraceEnabled()) {
                LOG.trace("Attempt to find timezone from session resulted in " + timeZone);
            }
        }

        // Finally, use the default
        if (timeZone == null) {
            timeZone = TimeZone.getDefault();

            if (LOG.isTraceEnabled()) {
                LOG.trace("timezone set to default timezone " + timeZone);
            }
        }

        if (SCRequestUtils.isOKtoUseSession(request)) {
            request.setAttribute(TIMEZONE_VAR, timeZone, WebRequest.SCOPE_GLOBAL_SESSION);
        }
        return timeZone;
    }
}
