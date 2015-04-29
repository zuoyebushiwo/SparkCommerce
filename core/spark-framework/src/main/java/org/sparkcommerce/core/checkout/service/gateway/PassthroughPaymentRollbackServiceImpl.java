/*
 * #%L
 * SparkCommerce Framework
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
package org.sparkcommerce.core.checkout.service.gateway;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sparkcommerce.common.money.Money;
import org.sparkcommerce.common.payment.PaymentGatewayType;
import org.sparkcommerce.common.payment.PaymentTransactionType;
import org.sparkcommerce.common.payment.PaymentType;
import org.sparkcommerce.common.payment.dto.PaymentRequestDTO;
import org.sparkcommerce.common.payment.dto.PaymentResponseDTO;
import org.sparkcommerce.common.payment.service.PaymentGatewayRollbackService;
import org.sparkcommerce.common.vendor.service.exception.PaymentException;
import org.sparkcommerce.core.order.service.OrderService;
import org.sparkcommerce.core.payment.service.OrderPaymentService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * This default implementation will create a compensating transaction response based on the transaction type passed in
 * for any Passthrough Order Payments on the order.
 * This is by default initiated from SparkCheckoutController.processPassthroughCheckout();
 * If an error occurs in the checkout workflow, the {@link ConfirmPaymentsRollbackHandler} gets invoked and will call this class.
 *
 * @author Elbert Bautista (elbertbautista)
 */
@Service("blPassthroughPaymentRollbackService")
public class PassthroughPaymentRollbackServiceImpl implements PaymentGatewayRollbackService {

    protected static final Log LOG = LogFactory.getLog(PassthroughPaymentRollbackServiceImpl.class);

    @Resource(name = "blOrderService")
    protected OrderService orderService;

    @Resource(name = "blOrderPaymentService")
    protected OrderPaymentService orderPaymentService;

    @Override
    public PaymentResponseDTO rollbackAuthorize(PaymentRequestDTO transactionToBeRolledBack) throws PaymentException {

        if (LOG.isTraceEnabled()) {
            LOG.trace("Passthrough Payment Gateway - Rolling back authorize transaction with amount: " + transactionToBeRolledBack.getTransactionTotal());
        }

        if (transactionToBeRolledBack.getAdditionalFields().containsKey(PassthroughPaymentConstants.PASSTHROUGH_PAYMENT_TYPE)) {

            return new PaymentResponseDTO(
                    PaymentType.getInstance((String)transactionToBeRolledBack.getAdditionalFields().get(PassthroughPaymentConstants.PASSTHROUGH_PAYMENT_TYPE)),
                    PaymentGatewayType.PASSTHROUGH)
                    .rawResponse("rollback authorize - successful")
                    .successful(true)
                    .paymentTransactionType(PaymentTransactionType.REVERSE_AUTH)
                    .amount(new Money(transactionToBeRolledBack.getTransactionTotal()));

        }

        throw new PaymentException("Make sure transaction contains a Passthrough Payment Type");
    }

    @Override
    public PaymentResponseDTO rollbackCapture(PaymentRequestDTO transactionToBeRolledBack) throws PaymentException {
        throw new PaymentException("The Rollback Capture method is not supported for this module");
    }

    @Override
    public PaymentResponseDTO rollbackAuthorizeAndCapture(PaymentRequestDTO transactionToBeRolledBack) throws PaymentException {
        if (LOG.isTraceEnabled()) {
            LOG.trace("Passthrough Payment Gateway - Rolling back authorize and capture transaction with amount: " + transactionToBeRolledBack.getTransactionTotal());
        }

        if (transactionToBeRolledBack.getAdditionalFields().containsKey(PassthroughPaymentConstants.PASSTHROUGH_PAYMENT_TYPE)) {

            return new PaymentResponseDTO(
                    PaymentType.getInstance((String)transactionToBeRolledBack.getAdditionalFields().get(PassthroughPaymentConstants.PASSTHROUGH_PAYMENT_TYPE)),
                    PaymentGatewayType.PASSTHROUGH)
                    .rawResponse("rollback authorize and capture - successful")
                    .successful(true)
                    .paymentTransactionType(PaymentTransactionType.VOID)
                    .amount(new Money(transactionToBeRolledBack.getTransactionTotal()));

        }

        throw new PaymentException("Make sure transaction contains a Passthrough Payment Type");
    }

    @Override
    public PaymentResponseDTO rollbackRefund(PaymentRequestDTO transactionToBeRolledBack) throws PaymentException {
        throw new PaymentException("The Rollback Refund method is not supported for this module");
    }
}
