/*
 * #%L
 * SparkCommerce Integration
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

import org.apache.commons.lang.time.DateUtils;
import org.sparkcommerce.core.catalog.domain.Category;
import org.sparkcommerce.core.catalog.domain.Product;
import org.sparkcommerce.core.order.domain.Order;
import org.sparkcommerce.core.order.service.call.OrderItemRequestDTO;
import org.sparkcommerce.core.order.service.exception.AddToCartException;
import org.sparkcommerce.profile.core.domain.Customer;
import org.sparkcommerce.test.CommonSetupBaseTest;

import java.util.Date;

public class OrderBaseTest extends CommonSetupBaseTest {

    protected Customer createNamedCustomer() {
        Customer customer = customerService.createCustomerFromId(null);
        customer.setUsername(String.valueOf(customer.getId()));
        return customer;
    }
    
    public Order setUpNamedOrder() throws AddToCartException {
        Customer customer = customerService.saveCustomer(createNamedCustomer());

        Order order = orderService.createNamedOrderForCustomer("Boxes Named Order", customer);
        
        Product newProduct = addTestProduct("Cube Box", "Boxes");        
        Category newCategory = newProduct.getDefaultCategory();
        
        order = orderService.addItem(order.getId(), 
                new OrderItemRequestDTO(newProduct.getId(), newProduct.getDefaultSku().getId(), newCategory.getId(), 2), 
                true);

        return order;
    }
    
    public Order setUpCartWithActiveSku() throws AddToCartException {
        Customer customer = customerService.saveCustomer(createNamedCustomer());

        Order order = orderService.createNewCartForCustomer(customer);

        Product newProduct = addTestProduct("Plastic Crate Active", "Crates");
        Category newCategory = newProduct.getDefaultCategory();
        
        order = orderService.addItem(order.getId(), 
                new OrderItemRequestDTO(newProduct.getId(), newProduct.getDefaultSku().getId(), newCategory.getId(), 1), 
                true);

        return order;
    }
    
    public Order setUpCartWithInactiveSku() throws AddToCartException {
        Customer customer = customerService.saveCustomer(createNamedCustomer());

        Order order = orderService.createNewCartForCustomer(customer);

        Product newProduct = addTestProduct("Plastic Crate Should Be Inactive", "Crates");
        Category newCategory = newProduct.getDefaultCategory();
        
        order = orderService.addItem(order.getId(), 
                new OrderItemRequestDTO(newProduct.getId(), newProduct.getDefaultSku().getId(), newCategory.getId(), 1), 
                true);
        
        // Make the SKU inactive
        newProduct.getDefaultSku().setActiveEndDate(DateUtils.addDays(new Date(), -1));
        catalogService.saveSku(newProduct.getDefaultSku());

        return order;
    }
    
}
