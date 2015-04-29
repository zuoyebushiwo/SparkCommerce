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
 * <p>This is a decoupled interface that provides
 * the basic functions needed to create the normal BILLABLE Credit Card Transactions</p>
 *
 * <p>The intention of these method implementations are to make a Server to Server API call.
 * Depending on the Gateway implementation, the overall goal and meaning of the method may vary:
 * For example, a module can implement the AUTHORIZE method:
 * <ul>
 * <li>Either to send Credit Card information directly (Server to Server) to the gateway to perform the transaction</li>
 * <li>Or to confirm an AUTHORIZATION process (some gateways dont handle a token based process through a Transparent Redirect)</li>
 * <li>OR handle both (the implementation will do one or the other based on the passed in parameters)</li>
 * </ul>
 * </p>
 *
 * <p>Please check the documentation of the implementing module to determine intended goal.</p>
 *
 * <p>Note: in the case where a gateway doesn't support confirming the transaction before it is submitted
 * (i.e. paymentGatewayConfigurationService.completeCheckoutOnCallback() == true)
 * The PaymentGatewayWebResponseService will handle translation of the final transaction response from the gateway.
 * There is no need to re-call this service if the gateway doesn't support confirming the transaction.</p>
 *
 * @see {@link PaymentGatewayWebResponseService}
 *
 * @author Anand Dasari
 */
public interface PaymentGatewayTransactionService {

    public PaymentResponseDTO authorize(PaymentRequestDTO paymentRequestDTO) throws PaymentException;

    public PaymentResponseDTO capture(PaymentRequestDTO paymentRequestDTO) throws PaymentException ;

    public PaymentResponseDTO authorizeAndCapture(PaymentRequestDTO paymentRequestDTO) throws PaymentException ;

    public PaymentResponseDTO reverseAuthorize(PaymentRequestDTO paymentRequestDTO) throws PaymentException;

    public PaymentResponseDTO refund(PaymentRequestDTO paymentRequestDTO) throws PaymentException;

    public PaymentResponseDTO voidPayment(PaymentRequestDTO paymentRequestDTO) throws PaymentException;

}
