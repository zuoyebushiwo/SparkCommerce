/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.security.channel;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.channel.InsecureChannelProcessor;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;

/**
 * <p>Very similar to the {@link InsecureChannelProcessor} except that instead of relying on only the HttpServletRequest
 * this also allows
 * inspection of the X-Forwarded-Proto header to determine if the request is secure. This class is required when the
 * application is deployed to an environment where SSL termination happens at a layer above the servlet container
 * (like at a load balancer)</p>
 * 
 * <p>This is intended to be used in conjunction with the {@link ProtoChannelBeanPostProcessor}. See that class for
 * more information on how to configure.</p>
 * 
 * <p>This class encapsulates functionality given in {@link InsecureChannelProcessor} so it is unnecessary to configure
 * both</p>
 * 
 * @author Joel Dasari
 * @see {@link InsecureChannelProcessor}
 * @see {@link ProtoSecureChannelProcessor}
 */
public class ProtoInsecureChannelProcessor extends InsecureChannelProcessor {

    @Override
    public void decide(FilterInvocation invocation, Collection<ConfigAttribute> config) throws IOException, ServletException {
        if ((invocation == null) || (config == null)) {
            throw new IllegalArgumentException("Nulls cannot be provided");
        }

        for (ConfigAttribute attribute : config) {
            if (supports(attribute)) {
                if (invocation.getHttpRequest().getHeader("X-Forwarded-Proto") != null
                        && "https".equalsIgnoreCase(invocation.getHttpRequest().getHeader("X-Forwarded-Proto"))) {
                    //We can't rely entirely on "!invocation.getHttpRequest().isSecure()" because many times, 
                    //when SSL terminates somewhere else, the proxied request will not be secure.
                    //In this case, someone may have gone to a secured page, and then tried to go back to an unsecured page.
                    getEntryPoint().commence(invocation.getRequest(), invocation.getResponse());
                } else if (invocation.getHttpRequest().getHeader("X-Forwarded-Proto") != null
                        && "http".equalsIgnoreCase(invocation.getHttpRequest().getHeader("X-Forwarded-Proto"))) {
                    return;
                } else if (!invocation.getHttpRequest().isSecure()) {
                    return;
                } else {
                    getEntryPoint().commence(invocation.getRequest(), invocation.getResponse());
                }
            }
        }
    }
}
