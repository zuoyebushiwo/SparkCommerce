/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */

package org.sparkcommerce.common.web.payment.processor;

import org.sparkcommerce.common.extension.ExtensionManager;
import org.springframework.stereotype.Service;

/**
 * @author Anand Dasari
 */
@Service("blCreditCardTypesExtensionManager")
public class CreditCardTypesExtensionManager extends ExtensionManager<CreditCardTypesExtensionHandler> {

    public CreditCardTypesExtensionManager() {
        super(CreditCardTypesExtensionHandler.class);
    }

    @Override
    public boolean continueOnHandled() {
        return true;
    }

}
