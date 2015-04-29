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

import org.sparkcommerce.core.order.domain.Order;
import org.sparkcommerce.core.order.service.OrderItemService;
import org.sparkcommerce.core.order.service.OrderService;
import org.sparkcommerce.core.order.service.call.DiscreteOrderItemRequest;
import org.sparkcommerce.core.order.service.call.OrderItemRequestDTO;
import org.sparkcommerce.core.order.service.workflow.CartOperationRequest;
import org.sparkcommerce.core.workflow.BaseActivity;
import org.sparkcommerce.core.workflow.ProcessContext;

import javax.annotation.Resource;

public class UpdateProductOptionsOrderItemActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {
    
    @Resource(name = "blOrderService")
    protected OrderService orderService;

    @Resource(name = "blOrderItemService")
    protected OrderItemService orderItemService;

    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();
        OrderItemRequestDTO orderItemRequestDTO = request.getItemRequest();
        Order order = request.getOrder();
        
        if (orderItemService.readOrderItemById(Long.valueOf(orderItemRequestDTO.getOrderItemId())) != null) {
            DiscreteOrderItemRequest itemRequest = new DiscreteOrderItemRequest();
            itemRequest.setItemAttributes(orderItemRequestDTO.getItemAttributes());
            orderItemService.updateDiscreteOrderItem(orderItemService.readOrderItemById(Long.valueOf(orderItemRequestDTO.getOrderItemId())), itemRequest);

        }

        order = orderService.save(order, false);
        request.setOrder(order);

        return context;
    }

}
