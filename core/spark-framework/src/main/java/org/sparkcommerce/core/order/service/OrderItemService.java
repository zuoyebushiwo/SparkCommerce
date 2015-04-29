/*
 * #%L
 * SparkCommerce Framework
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
package org.sparkcommerce.core.order.service;

import org.sparkcommerce.core.order.domain.BundleOrderItem;
import org.sparkcommerce.core.order.domain.DiscreteOrderItem;
import org.sparkcommerce.core.order.domain.GiftWrapOrderItem;
import org.sparkcommerce.core.order.domain.OrderItem;
import org.sparkcommerce.core.order.domain.PersonalMessage;
import org.sparkcommerce.core.order.service.call.BundleOrderItemRequest;
import org.sparkcommerce.core.order.service.call.DiscreteOrderItemRequest;
import org.sparkcommerce.core.order.service.call.GiftWrapOrderItemRequest;
import org.sparkcommerce.core.order.service.call.OrderItemRequest;
import org.sparkcommerce.core.order.service.call.OrderItemRequestDTO;
import org.sparkcommerce.core.order.service.call.ProductBundleOrderItemRequest;

import java.util.HashMap;

public interface OrderItemService {
    
    public OrderItem readOrderItemById(Long orderItemId);

    public OrderItem saveOrderItem(OrderItem orderItem);
    
    public void delete(OrderItem item);
    
    public PersonalMessage createPersonalMessage();

    public DiscreteOrderItem createDiscreteOrderItem(DiscreteOrderItemRequest itemRequest);
    
    public DiscreteOrderItem createDynamicPriceDiscreteOrderItem(final DiscreteOrderItemRequest itemRequest, @SuppressWarnings("rawtypes") HashMap skuPricingConsiderations);

    public GiftWrapOrderItem createGiftWrapOrderItem(GiftWrapOrderItemRequest itemRequest);

    /**
     * Used to create "manual" product bundles.   Manual product bundles are primarily designed
     * for grouping items in the cart display.    Typically ProductBundle will be used to
     * achieve non programmer related bundles.
     *
     *
     * @param itemRequest
     * @return
     */
    public BundleOrderItem createBundleOrderItem(BundleOrderItemRequest itemRequest);

    public BundleOrderItem createBundleOrderItem(ProductBundleOrderItemRequest itemRequest);

    public BundleOrderItem createBundleOrderItem(ProductBundleOrderItemRequest itemRequest, boolean saveItem);

    /**
     * Creates an OrderItemRequestDTO object that most closely resembles the given OrderItem.
     * That is, it will copy the SKU and quantity and attempt to copy the product and category
     * if they exist.
     * 
     * @param item the item to copy
     * @return the OrderItemRequestDTO that mirrors the item
     */
    public OrderItemRequestDTO buildOrderItemRequestDTOFromOrderItem(OrderItem item);

    public OrderItem updateDiscreteOrderItem(OrderItem orderItem, DiscreteOrderItemRequest itemRequest);

    public OrderItem createOrderItem(OrderItemRequest itemRequest);


}
