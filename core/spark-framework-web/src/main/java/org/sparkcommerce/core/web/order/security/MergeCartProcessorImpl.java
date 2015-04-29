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
package org.sparkcommerce.core.web.order.security;

import org.sparkcommerce.common.security.MergeCartProcessor;
import org.sparkcommerce.common.util.SCRequestUtils;
import org.sparkcommerce.core.order.domain.Order;
import org.sparkcommerce.core.order.service.MergeCartService;
import org.sparkcommerce.core.order.service.OrderService;
import org.sparkcommerce.core.order.service.call.MergeCartResponse;
import org.sparkcommerce.core.order.service.exception.RemoveFromCartException;
import org.sparkcommerce.core.pricing.service.exception.PricingException;
import org.sparkcommerce.profile.core.domain.Customer;
import org.sparkcommerce.profile.core.service.CustomerService;
import org.sparkcommerce.profile.web.core.security.CustomerStateRequestProcessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @deprecated this has been replaced by invoking {@link MergeCartService} explicitly within the
 * {@link CartStateRequestProcessor}
 */
@Deprecated
@Component("blMergeCartProcessor")
public class MergeCartProcessorImpl implements MergeCartProcessor {

    protected String mergeCartResponseKey = "bl_merge_cart_response";

    @Resource(name="blCustomerService")
    protected CustomerService customerService;

    @Resource(name="blOrderService")
    protected OrderService orderService;
    
    @Resource(name="blMergeCartService")
    protected MergeCartService mergeCartService;
    
    @Resource(name = "blCustomerStateRequestProcessor")
    protected CustomerStateRequestProcessor customerStateRequestProcessor;
    
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, Authentication authResult) {
        execute(new ServletWebRequest(request, response), authResult);
    }
    
    @Override
    public void execute(WebRequest request, Authentication authResult) {
        Customer loggedInCustomer = customerService.readCustomerByUsername(authResult.getName());
        Customer anonymousCustomer = customerStateRequestProcessor.getAnonymousCustomer(request);
        
        Order cart = null;
        if (anonymousCustomer != null) {
            cart = orderService.findCartForCustomer(anonymousCustomer);
        }
        MergeCartResponse mergeCartResponse;
        try {
            mergeCartResponse = mergeCartService.mergeCart(loggedInCustomer, cart);
        } catch (PricingException e) {
            throw new RuntimeException(e);
        } catch (RemoveFromCartException e) {
            throw new RuntimeException(e);
        }

        if (SCRequestUtils.isOKtoUseSession(request)) {
            request.setAttribute(mergeCartResponseKey, mergeCartResponse, WebRequest.SCOPE_GLOBAL_SESSION);
        }
    }

    public String getMergeCartResponseKey() {
        return mergeCartResponseKey;
    }

    public void setMergeCartResponseKey(String mergeCartResponseKey) {
        this.mergeCartResponseKey = mergeCartResponseKey;
    }

}
