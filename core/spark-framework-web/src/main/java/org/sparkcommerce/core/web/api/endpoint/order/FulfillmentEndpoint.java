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
package org.sparkcommerce.core.web.api.endpoint.order;

import org.sparkcommerce.core.checkout.service.CheckoutService;
import org.sparkcommerce.core.order.domain.FulfillmentGroup;
import org.sparkcommerce.core.order.domain.FulfillmentOption;
import org.sparkcommerce.core.order.domain.Order;
import org.sparkcommerce.core.order.domain.OrderItem;
import org.sparkcommerce.core.order.service.FulfillmentGroupService;
import org.sparkcommerce.core.order.service.FulfillmentOptionService;
import org.sparkcommerce.core.order.service.OrderService;
import org.sparkcommerce.core.order.service.call.FulfillmentGroupItemRequest;
import org.sparkcommerce.core.order.service.call.FulfillmentGroupRequest;
import org.sparkcommerce.core.order.service.type.FulfillmentType;
import org.sparkcommerce.core.pricing.service.exception.PricingException;
import org.sparkcommerce.core.web.api.SparkWebServicesException;
import org.sparkcommerce.core.web.api.endpoint.BaseEndpoint;
import org.sparkcommerce.core.web.api.wrapper.FulfillmentGroupItemWrapper;
import org.sparkcommerce.core.web.api.wrapper.FulfillmentGroupWrapper;
import org.sparkcommerce.core.web.api.wrapper.FulfillmentOptionWrapper;
import org.sparkcommerce.core.web.api.wrapper.OrderWrapper;
import org.sparkcommerce.core.web.order.CartState;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

/**
 * This endpoint depends on JAX-RS.  It should be extended by components that actually wish 
 * to provide an endpoint.  The annotations such as @Path, @Scope, @Context, @PathParam, @QueryParam, 
 * @GET, @POST, @PUT, and @DELETE are purposely not provided here to allow implementors finer control over 
 * the details of the endpoint.
 * <p/>
 * User: Kelly Tisdell
 * Date: 4/10/12
 */
public abstract class FulfillmentEndpoint extends BaseEndpoint {

    @Resource(name="blCheckoutService")
    protected CheckoutService checkoutService;

    @Resource(name="blOrderService")
    protected OrderService orderService;
    
    @Resource(name="blFulfillmentGroupService")
    protected FulfillmentGroupService fulfillmentGroupService;

    @Resource(name = "blFulfillmentOptionService")
    protected FulfillmentOptionService fulfillmentOptionService;

    public List<FulfillmentGroupWrapper> findFulfillmentGroupsForOrder(HttpServletRequest request) {
        Order cart = CartState.getCart();
        if (cart != null && cart.getFulfillmentGroups() != null && !cart.getFulfillmentGroups().isEmpty()) {
            List<FulfillmentGroup> fulfillmentGroups = cart.getFulfillmentGroups();
            List<FulfillmentGroupWrapper> fulfillmentGroupWrappers = new ArrayList<FulfillmentGroupWrapper>();
            for (FulfillmentGroup fulfillmentGroup : fulfillmentGroups) {
                FulfillmentGroupWrapper fulfillmentGroupWrapper = (FulfillmentGroupWrapper) context.getBean(FulfillmentGroupWrapper.class.getName());
                fulfillmentGroupWrapper.wrapSummary(fulfillmentGroup, request);
                fulfillmentGroupWrappers.add(fulfillmentGroupWrapper);
            }
            return fulfillmentGroupWrappers;
        }
        throw SparkWebServicesException.build(Response.Status.NOT_FOUND.getStatusCode())
                .addMessage(SparkWebServicesException.CART_NOT_FOUND);
    }

    public OrderWrapper removeAllFulfillmentGroupsFromOrder(HttpServletRequest request,
            boolean priceOrder) {
        Order cart = CartState.getCart();
        if (cart != null) {
            try {
                fulfillmentGroupService.removeAllFulfillmentGroupsFromOrder(cart, priceOrder);
                OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
                wrapper.wrapDetails(cart, request);
                return wrapper;
            } catch (PricingException e) {
                throw SparkWebServicesException.build(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), null, null, e)
                        .addMessage(SparkWebServicesException.CART_PRICING_ERROR);
            }
        }
        throw SparkWebServicesException.build(Response.Status.NOT_FOUND.getStatusCode())
                .addMessage(SparkWebServicesException.CART_NOT_FOUND);

    }

    public FulfillmentGroupWrapper addFulfillmentGroupToOrder(HttpServletRequest request,
            FulfillmentGroupWrapper wrapper,
            boolean priceOrder) {
        Order cart = CartState.getCart();
        if (cart != null) {
            FulfillmentGroupRequest fulfillmentGroupRequest = wrapper.unwrap(request, context);

            if (fulfillmentGroupRequest.getOrder() != null && fulfillmentGroupRequest.getOrder().getId().equals(cart.getId())) {
                try {
                    fulfillmentGroupRequest.setOrder(cart);
                    FulfillmentGroup fulfillmentGroup = fulfillmentGroupService.addFulfillmentGroupToOrder(fulfillmentGroupRequest, priceOrder);
                    FulfillmentGroupWrapper fulfillmentGroupWrapper = (FulfillmentGroupWrapper) context.getBean(FulfillmentGroupWrapper.class.getName());
                    fulfillmentGroupWrapper.wrapDetails(fulfillmentGroup, request);
                    return fulfillmentGroupWrapper;
                } catch (PricingException e) {
                    throw SparkWebServicesException.build(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), null, null, e)
                            .addMessage(SparkWebServicesException.CART_PRICING_ERROR);
                }
            }
        }
        throw SparkWebServicesException.build(Response.Status.NOT_FOUND.getStatusCode())
                .addMessage(SparkWebServicesException.CART_NOT_FOUND);

    }

    public FulfillmentGroupWrapper addItemToFulfillmentGroup(HttpServletRequest request,
            Long fulfillmentGroupId,
            FulfillmentGroupItemWrapper wrapper,
            boolean priceOrder) {
        Order cart = CartState.getCart();
        if (cart != null) {
            FulfillmentGroupItemRequest fulfillmentGroupItemRequest = wrapper.unwrap(request, context);
            if (fulfillmentGroupItemRequest.getOrderItem() != null) {
                FulfillmentGroup fulfillmentGroup = null;
                OrderItem orderItem = null;

                for (FulfillmentGroup fg : cart.getFulfillmentGroups()) {
                    if (fg.getId().equals(fulfillmentGroupId)) {
                        fulfillmentGroup = fg;
                    }
                }
                fulfillmentGroupItemRequest.setFulfillmentGroup(fulfillmentGroup);

                for (OrderItem oi : cart.getOrderItems()) {
                    if (oi.getId().equals(fulfillmentGroupItemRequest.getOrderItem().getId())) {
                        orderItem = oi;
                    }
                }
                fulfillmentGroupItemRequest.setOrderItem(orderItem);

                if (fulfillmentGroup != null && orderItem != null) {
                    try {
                        FulfillmentGroup fg = fulfillmentGroupService.addItemToFulfillmentGroup(fulfillmentGroupItemRequest, priceOrder);
                        FulfillmentGroupWrapper fulfillmentGroupWrapper = (FulfillmentGroupWrapper) context.getBean(FulfillmentGroupWrapper.class.getName());
                        fulfillmentGroupWrapper.wrapDetails(fg, request);
                        return fulfillmentGroupWrapper;

                    } catch (PricingException e) {
                        throw SparkWebServicesException.build(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), null, null, e)
                                .addMessage(SparkWebServicesException.CART_PRICING_ERROR);
                    }
                }
            }
        }
        throw SparkWebServicesException.build(Response.Status.NOT_FOUND.getStatusCode())
                .addMessage(SparkWebServicesException.CART_NOT_FOUND);

    }

    public FulfillmentGroupWrapper addFulfillmentOptionToFulfillmentGroup(HttpServletRequest request,
            Long fulfillmentGroupId,
            Long fulfillmentOptionId,
            boolean priceOrder) {

        FulfillmentOption option = fulfillmentOptionService.readFulfillmentOptionById(fulfillmentOptionId);
        if (option == null) {
            throw SparkWebServicesException.build(Response.Status.NOT_FOUND.getStatusCode())
                    .addMessage(SparkWebServicesException.FULFILLMENT_OPTION_NOT_FOUND, fulfillmentOptionId);
        }

        Order cart = CartState.getCart();
        if (cart != null) {
            boolean found = false;
            List<FulfillmentGroup> groups = cart.getFulfillmentGroups();
            if (groups != null && !groups.isEmpty()) {
                for (FulfillmentGroup group : groups) {
                    if (group.getId().equals(fulfillmentGroupId)) {
                        group.setFulfillmentOption(option);
                        found = true;
                        break;
                    }
                }
            }
            try {
                if (found) {
                    cart = orderService.save(cart, priceOrder);
                    for (FulfillmentGroup fg : groups) {
                        if (fg.getId().equals(fulfillmentGroupId)) {
                            FulfillmentGroupWrapper fulfillmentGroupWrapper = (FulfillmentGroupWrapper) context.getBean(FulfillmentGroupWrapper.class.getName());
                            fulfillmentGroupWrapper.wrapDetails(fg, request);
                            return fulfillmentGroupWrapper;
                        }
                    }
                } else {
                    throw SparkWebServicesException.build(Response.Status.NOT_FOUND.getStatusCode())
                            .addMessage(SparkWebServicesException.FULFILLMENT_GROUP_NOT_FOUND, fulfillmentGroupId);
                }
            } catch (PricingException e) {
                throw SparkWebServicesException.build(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), null, null, e)
                        .addMessage(SparkWebServicesException.CART_PRICING_ERROR);
            }
        }
        throw SparkWebServicesException.build(Response.Status.NOT_FOUND.getStatusCode())
                .addMessage(SparkWebServicesException.CART_NOT_FOUND);
    }

    public List<FulfillmentOptionWrapper> findFulfillmentOptions(HttpServletRequest request, String fulfillmentType) {
        ArrayList<FulfillmentOptionWrapper> out = new ArrayList<FulfillmentOptionWrapper>();
        List<FulfillmentOption> options = null;
        FulfillmentType type = FulfillmentType.getInstance(fulfillmentType);
        if (type != null) {
            options = fulfillmentOptionService.readAllFulfillmentOptionsByFulfillmentType(type);
        } else {
            options = fulfillmentOptionService.readAllFulfillmentOptions();
        }
        
        for (FulfillmentOption option : options) {
            FulfillmentOptionWrapper fulfillmentOptionWrapper = (FulfillmentOptionWrapper) context.getBean(FulfillmentOptionWrapper.class.getName());
            fulfillmentOptionWrapper.wrapDetails(option, request);
            out.add(fulfillmentOptionWrapper);
        }
        
        return out;
    }
}
