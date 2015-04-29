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
package org.sparkcommerce.core.pricing.service;

import javax.annotation.Resource;

import org.sparkcommerce.core.order.domain.Order;
import org.sparkcommerce.core.pricing.service.exception.PricingException;
import org.sparkcommerce.core.workflow.ProcessContext;
import org.sparkcommerce.core.workflow.Processor;
import org.sparkcommerce.core.workflow.WorkflowException;
import org.springframework.stereotype.Service;

@Service("blPricingService")
public class PricingServiceImpl implements PricingService {

    @Resource(name="blPricingWorkflow")
    protected Processor pricingWorkflow;

    public Order executePricing(Order order) throws PricingException {
        try {
            ProcessContext<Order> context = (ProcessContext<Order>) pricingWorkflow.doActivities(order);
            Order response = context.getSeedData();

            return response;
        } catch (WorkflowException e) {
            throw new PricingException("Unable to execute pricing for order -- id: " + order.getId(), e);
        }
    }

}
