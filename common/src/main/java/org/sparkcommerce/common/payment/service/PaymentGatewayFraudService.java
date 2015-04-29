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

/**
 * <p>Certain Payment Integrations allow you to use Fraud Services like Address Verification and Buyer Authentication,
 * such as PayPal Payments Pro (PayFlow Edition)</p>
 *
 * <p>This API allows you to call certain fraud prevention APIs exposed from the gateway.</p>
 *
 * @author Anand Dasari
 */
public interface PaymentGatewayFraudService {

    /**
     * Certain Gateways integrate with Visa's Verified by Visa and MasterCard's SecureCode API
     * If the buyer is enrolled in such a service, we will need to redirect the buyer's browser
     * to the ACS ( Access Control Server, eg. users' bank) for verification.
     * See: http://en.wikipedia.org/wiki/3-D_Secure
     *
     * This method is intended to retrieve a URL to the ACS from the gateway.
     *
     * @param paymentRequestDTO
     * @return
     */
    public PaymentResponseDTO requestPayerAuthentication(PaymentRequestDTO paymentRequestDTO);

}
