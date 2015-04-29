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
@Service("blTRCreditCardExtensionManager")
public class TRCreditCardExtensionManager extends ExtensionManager<TRCreditCardExtensionHandler> {

    public TRCreditCardExtensionManager() {
        super(TRCreditCardExtensionHandler.class);
    }

    @Override
    public boolean continueOnHandled() {
        return true;
    }

}