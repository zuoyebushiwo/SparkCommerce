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
 * <p>The purpose of this class, is to provide an API that will create
 * any gateway specific parameters needed for a Transparent Redirect/Silent Order Post etc...</p>
 *
 * <p>Some payment gateways provide this ability and will generate either a Secure Token
 * or some hashed parameters that will be placed as hidden fields on your Credit Card form.
 * These parameters (along with the Credit Card information) will be placed on the ResponseDTO
 * and your HTML should include these fields to be POSTed directly to the
 * implementing gateway for processing.</p>
 *
 * @author Anand Dasari
 */
public interface PaymentGatewayTransparentRedirectService {

    public PaymentResponseDTO createAuthorizeForm(PaymentRequestDTO requestDTO) throws PaymentException;

    public PaymentResponseDTO createAuthorizeAndCaptureForm(PaymentRequestDTO requestDTO) throws PaymentException;

}
