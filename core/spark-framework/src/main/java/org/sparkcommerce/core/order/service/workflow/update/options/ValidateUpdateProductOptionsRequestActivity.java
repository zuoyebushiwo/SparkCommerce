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
package org.sparkcommerce.core.order.service.workflow.update.options;

import org.sparkcommerce.core.order.domain.DiscreteOrderItem;
import org.sparkcommerce.core.order.domain.OrderItem;
import org.sparkcommerce.core.order.service.OrderItemService;
import org.sparkcommerce.core.order.service.call.OrderItemRequestDTO;
import org.sparkcommerce.core.order.service.workflow.CartOperationRequest;
import org.sparkcommerce.core.workflow.BaseActivity;
import org.sparkcommerce.core.workflow.ProcessContext;

import javax.annotation.Resource;

public class ValidateUpdateProductOptionsRequestActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {
    
    @Resource(name = "blOrderItemService")
    protected OrderItemService orderItemService;
    
    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();
        OrderItemRequestDTO orderItemRequestDTO = request.getItemRequest();
        
        // Throw an exception if the user did not specify an orderItemId
        if (orderItemRequestDTO.getOrderItemId() == null) {
            throw new IllegalArgumentException("OrderItemId must be specified to locate the order item");
        }

        // Throw an exception if the user did not specify an order to add the item to
        if (request.getOrder() == null) {
            throw new IllegalArgumentException("Order is required when updating items in the order");
        }
        
        // Throw an exception if the user is trying to update an order item that is part of a bundle
        OrderItem orderItem = orderItemService.readOrderItemById(orderItemRequestDTO.getOrderItemId());
        if (orderItem != null && orderItem instanceof DiscreteOrderItem) {
            DiscreteOrderItem doi = (DiscreteOrderItem) orderItem;
            if (doi.getBundleOrderItem() != null) {
                //then its ok , since we are just updating the product options
            }
        }
        
        return context;
    }
    
}
