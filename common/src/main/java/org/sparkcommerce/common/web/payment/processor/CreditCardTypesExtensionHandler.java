/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */

package org.sparkcommerce.common.web.payment.processor;

import org.sparkcommerce.common.extension.ExtensionHandler;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;

import java.util.Map;

/**
 * @author Anand Dasari
 */
public interface CreditCardTypesExtensionHandler extends ExtensionHandler {

    /**
     * The registered Extension Handler will populate any specific Payment Gateway
     * codes required for Credit Card Types.
     *
     * key = "Card Type Code to send to the Gateway"
     * value = "Friendly Name of Card type (e.g. Visa, MasterCard, etc...)"
     *
     * @param creditCardTypes
     * @return
     */
    public ExtensionResultStatusType populateCreditCardMap(Map<String, String> creditCardTypes);

}
