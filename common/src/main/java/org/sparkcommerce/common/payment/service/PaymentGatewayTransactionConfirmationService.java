/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.payment.service;

import org.sparkcommerce.common.payment.dto.PaymentRequestDTO;
import org.sparkcommerce.common.payment.dto.PaymentResponseDTO;
import org.sparkcommerce.common.vendor.service.exception.PaymentException;

/**
 * <p>
 * This API is intended to be called by the Checkout Workflow
 * to confirm all Payments on the order that have not yet been confirmed/finalized.
 * In the case where an error is thrown by the gateway and confirming is not possible,
 * the workflow should invoke the rollback handlers on any Payments that have already been
 * successfully confirmed.
 * </p>
 *
 * <p>
 * Not all Gateways allow confirmation. That setting can be found on the
 * PaymentGatewayConfiguration.completeCheckoutOnCallback(). If this value is set to true,
 * then the gateway does not support confirming the transaction, as it assumes to be the final step
 * in the completion process. Most Credit Card integrations do not support confirming the transaction,
 * Third Party providers like PayPal Express, or the SC Gift Card Module do and should implement
 * this interface.
 * </p>
 *
 * @see {@link PaymentGatewayRollbackService}
 * @see {@link PaymentGatewayConfiguration}
 *
 * @author Anand Dasari
 */
public interface PaymentGatewayTransactionConfirmationService {

    public PaymentResponseDTO confirmTransaction(PaymentRequestDTO paymentRequestDTO) throws PaymentException;

}
