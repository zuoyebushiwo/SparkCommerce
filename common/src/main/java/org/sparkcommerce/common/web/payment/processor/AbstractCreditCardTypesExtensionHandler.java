/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web.payment.processor;

import org.sparkcommerce.common.extension.AbstractExtensionHandler;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;

import java.util.Map;

/**
 * @author Elbert Bautista (elbertbautista)
 */
public abstract class AbstractCreditCardTypesExtensionHandler extends AbstractExtensionHandler
        implements CreditCardTypesExtensionHandler {

    @Override
    public ExtensionResultStatusType populateCreditCardMap(Map<String, String> creditCardTypes) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
