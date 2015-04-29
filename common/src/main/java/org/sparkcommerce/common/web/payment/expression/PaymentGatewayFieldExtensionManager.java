/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */

package org.sparkcommerce.common.web.payment.expression;

import org.sparkcommerce.common.extension.ExtensionManager;
import org.springframework.stereotype.Service;

/**
 * @author Anand Dasari
 */
@Service("blPaymentGatewayFieldExtensionManager")
public class PaymentGatewayFieldExtensionManager extends ExtensionManager<PaymentGatewayFieldExtensionHandler> {

    public PaymentGatewayFieldExtensionManager() {
        super(PaymentGatewayFieldExtensionHandler.class);
    }

    @Override
    public boolean continueOnHandled() {
        return true;
    }

}
