/*
 * #%L
 * SparkCommerce Integration
 * %%
 * Copyright (C) 2009 - 2014 Spark Commerce
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
package org.sparkcommerce.core.payment.service;

import org.sparkcommerce.common.payment.service.PaymentGatewayConfiguration;
import org.sparkcommerce.common.payment.service.PaymentGatewayConfigurationService;
import org.sparkcommerce.common.payment.service.PaymentGatewayCreditCardService;
import org.sparkcommerce.common.payment.service.PaymentGatewayCustomerService;
import org.sparkcommerce.common.payment.service.PaymentGatewayFraudService;
import org.sparkcommerce.common.payment.service.PaymentGatewayHostedService;
import org.sparkcommerce.common.payment.service.PaymentGatewayReportingService;
import org.sparkcommerce.common.payment.service.PaymentGatewayRollbackService;
import org.sparkcommerce.common.payment.service.PaymentGatewaySubscriptionService;
import org.sparkcommerce.common.payment.service.PaymentGatewayTransactionConfirmationService;
import org.sparkcommerce.common.payment.service.PaymentGatewayTransactionService;
import org.sparkcommerce.common.payment.service.PaymentGatewayTransparentRedirectService;
import org.sparkcommerce.common.payment.service.PaymentGatewayWebResponseService;
import org.sparkcommerce.common.web.payment.expression.PaymentGatewayFieldExtensionHandler;
import org.sparkcommerce.common.web.payment.processor.CreditCardTypesExtensionHandler;
import org.sparkcommerce.common.web.payment.processor.TRCreditCardExtensionHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Elbert Bautista (elbertbautista)
 */
@Service("blNullPaymentGatewayConfigurationService")
public class NullPaymentGatewayConfigurationServiceImpl implements PaymentGatewayConfigurationService {

    @Resource(name = "blNullPaymentGatewayConfiguration")
    protected NullPaymentGatewayConfiguration configuration;

    @Resource(name = "blNullPaymentGatewayRollbackService")
    protected PaymentGatewayRollbackService rollbackService;

    public PaymentGatewayConfiguration getConfiguration() {
        return configuration;
    }

    public PaymentGatewayTransactionService getTransactionService() {
        return null;
    }

    public PaymentGatewayTransactionConfirmationService getTransactionConfirmationService() {
        return null;
    }

    public PaymentGatewayReportingService getReportingService() {
        return null;
    }

    public PaymentGatewayCreditCardService getCreditCardService() {
        return null;
    }

    public PaymentGatewayCustomerService getCustomerService() {
        return null;
    }

    public PaymentGatewaySubscriptionService getSubscriptionService() {
        return null;
    }

    public PaymentGatewayFraudService getFraudService() {
        return null;
    }

    public PaymentGatewayHostedService getHostedService() {
        return null;
    }

    public PaymentGatewayRollbackService getRollbackService() {
        return rollbackService;
    }

    public PaymentGatewayWebResponseService getWebResponseService() {
        return null;
    }

    public PaymentGatewayTransparentRedirectService getTransparentRedirectService() {
        return null;
    }

    public TRCreditCardExtensionHandler getCreditCardExtensionHandler() {
        return null;
    }

    public PaymentGatewayFieldExtensionHandler getFieldExtensionHandler() {
        return null;
    }

    public CreditCardTypesExtensionHandler getCreditCardTypesExtensionHandler() {
        return null;
    }

}
