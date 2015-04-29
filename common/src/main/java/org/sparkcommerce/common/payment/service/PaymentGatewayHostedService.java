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
 * <p>Certain Payment Integrations allow you to use a Hosted Solution,
 * such as PayPal Express and SagePay Form.
 * This API allows you to create the call to send a user to the Gateway's Hosted page
 * and to capture and record transaction responses back from them.</p>
 *
 * @author Anand Dasari
 */
public interface PaymentGatewayHostedService {

    public PaymentResponseDTO requestHostedEndpoint(PaymentRequestDTO paymentRequestDTO) throws PaymentException;

}
