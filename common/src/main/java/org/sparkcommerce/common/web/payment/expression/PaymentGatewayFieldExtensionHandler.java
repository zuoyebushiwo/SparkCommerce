/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */

package org.sparkcommerce.common.web.payment.expression;

import org.sparkcommerce.common.extension.ExtensionHandler;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;

import java.util.Map;

/**
 * @author Anand Dasari
 */
public interface PaymentGatewayFieldExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType mapFieldName(String fieldNameKey, Map<String, String> fieldNameMap);

}
