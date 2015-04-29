/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */

package org.sparkcommerce.common.web.payment.expression;

import org.sparkcommerce.common.web.expression.SparkVariableExpression;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>A Thymeleaf Variable Expression implementation for Payment Gateway Specific fields.
 * The Payment Module specific names are invoked via the ExtensionManager.
 * Therefore, each module will need to register itself properly.</p>
 * <p>The input name mappings are those properties defined in the corresponding
 * DTOs.</p>
 *
 * <pre><code>
 * <input type="text" th:name="${#paymentGatewayField.mapName("creditCard.creditCardNum")}"/>
 * </code></pre>
 * translates to:
 *
 * <pre><code>
 * PayPal PayFlow Pro: <input type="text" name="CARDNUM"/>
 * Braintree:          <input type="text" name="transaction[credit_card][number]"/>
 * etc...
 * </code></pre>
 *
 * @see {@link org.sparkcommerce.common.payment.dto.PaymentRequestDTO}
 * @see {@link org.sparkcommerce.common.payment.dto.CreditCardDTO}
 * @see {@link org.sparkcommerce.common.payment.dto.AddressDTO}
 *
 * @author Anand Dasari
 */
public class PaymentGatewayFieldVariableExpression implements SparkVariableExpression {

    @Resource(name = "blPaymentGatewayFieldExtensionManager")
    protected PaymentGatewayFieldExtensionManager extensionManager;

    @Override
    public String getName() {
        return "paymentGatewayField";
    }

    public String mapName(String fieldName) {
        Map<String, String> fieldNameMap = new HashMap<String, String>();
        fieldNameMap.put(fieldName, fieldName);
        extensionManager.getProxy().mapFieldName(fieldName, fieldNameMap);
        return fieldNameMap.get(fieldName);
    }

    public PaymentGatewayFieldExtensionManager getExtensionManager() {
        return extensionManager;
    }

    public void setExtensionManager(PaymentGatewayFieldExtensionManager extensionManager) {
        this.extensionManager = extensionManager;
    }
}
