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
package org.sparkcommerce.core.checkout.service.workflow;

import org.apache.commons.lang.StringUtils;
import org.sparkcommerce.core.catalog.domain.Product;
import org.sparkcommerce.core.catalog.domain.ProductOption;
import org.sparkcommerce.core.catalog.service.type.ProductOptionValidationStrategyType;
import org.sparkcommerce.core.order.domain.BundleOrderItem;
import org.sparkcommerce.core.order.domain.DiscreteOrderItem;
import org.sparkcommerce.core.order.domain.Order;
import org.sparkcommerce.core.order.domain.OrderItem;
import org.sparkcommerce.core.order.domain.OrderItemAttribute;
import org.sparkcommerce.core.order.service.ProductOptionValidationService;
import org.sparkcommerce.core.order.service.call.ActivityMessageDTO;
import org.sparkcommerce.core.order.service.exception.ProductOptionValidationException;
import org.sparkcommerce.core.order.service.exception.RequiredAttributeNotProvidedException;
import org.sparkcommerce.core.order.service.type.MessageType;
import org.sparkcommerce.core.workflow.ActivityMessages;
import org.sparkcommerce.core.workflow.BaseActivity;
import org.sparkcommerce.core.workflow.ProcessContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * This is an required activity to valiate if required product options are in the order.
 * 
 * @author Priyesh Patel
 *
 */
public class ValidateProductOptionsActivity extends BaseActivity<ProcessContext<CheckoutSeed>> {



    @Resource(name = "blProductOptionValidationService")
    protected ProductOptionValidationService productOptionValidationService;


    @Override
    public ProcessContext<CheckoutSeed> execute(ProcessContext<CheckoutSeed> context) throws Exception {
        Order order = context.getSeedData().getOrder();
        List<DiscreteOrderItem> orderItems = new ArrayList<DiscreteOrderItem>();
        for (OrderItem i : order.getOrderItems()) {
            if (i instanceof DiscreteOrderItem) {
                orderItems.add((DiscreteOrderItem) i);
            } else if (i instanceof BundleOrderItem) {
                orderItems.addAll(((BundleOrderItem) i).getDiscreteOrderItems());
            } else
                continue;
        }
        for (DiscreteOrderItem i : orderItems) {
            Map<String, OrderItemAttribute> attributeValues = i.getOrderItemAttributes();
            Product product = i.getProduct();

            if (product != null && product.getProductOptions() != null && product.getProductOptions().size() > 0) {
                for (ProductOption productOption : product.getProductOptions()) {
                    if (productOption.getRequired() && (productOption.getProductOptionValidationStrategyType() == null ||
                            productOption.getProductOptionValidationStrategyType().getRank() <= getProductOptionValidationStrategyType().getRank())) {
                        if (attributeValues.get(productOption.getAttributeName()) == null || StringUtils.isEmpty(attributeValues.get(productOption.getAttributeName()).getValue())) {
                            throw new RequiredAttributeNotProvidedException("Unable to validate cart, product  (" + product.getId() + ") required attribute was not provided: " + productOption.getAttributeName(), productOption.getAttributeName());
                        }
                    }
                    if (productOption.getProductOptionValidationType() != null && (productOption.getProductOptionValidationStrategyType() == null || productOption.getProductOptionValidationStrategyType().getRank() <= getProductOptionValidationStrategyType().getRank())) {
                        productOptionValidationService.validate(productOption, attributeValues.get(productOption.getAttributeName()).getValue());
                    }
                    if ((productOption.getProductOptionValidationStrategyType() != null && productOption.getProductOptionValidationStrategyType().getRank() > getProductOptionValidationStrategyType().getRank()))
                    {
                        //we need to validate however, we will not error out since this message is 
                        try {
                            productOptionValidationService.validate(productOption, (attributeValues.get(productOption.getAttributeName()) != null) ? attributeValues.get(productOption.getAttributeName()).getValue() : null);
                        } catch (ProductOptionValidationException e) {
                            ActivityMessageDTO msg = new ActivityMessageDTO(MessageType.PRODUCT_OPTION.getType(), 1, e.getMessage());
                            msg.setErrorCode(productOption.getErrorCode());
                            ((ActivityMessages) context).getActivityMessages().add(msg);
                        }

                    }
                }

            }
        }
        return context;
    }

    public ProductOptionValidationStrategyType getProductOptionValidationStrategyType() {
        return ProductOptionValidationStrategyType.SUBMIT_ORDER;
    }


}
