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
 * <p>Several payment gateways allow you to manage Customer and Credit Card Information from the gateway allowing
 * you to create a transaction from the tokenized customer or payment method at a later date.
 * Note: These are usually extra features you need to pay for when you sign up with the Gateway</p>
 *
 * @author Anand Dasari
 */
public interface PaymentGatewayCustomerService {

    public PaymentResponseDTO createGatewayCustomer(PaymentRequestDTO requestDTO) throws PaymentException;

    public PaymentResponseDTO updateGatewayCustomer(PaymentRequestDTO requestDTO) throws PaymentException;

    public PaymentResponseDTO deleteGatewayCustomer(PaymentRequestDTO requestDTO) throws PaymentException;

}
