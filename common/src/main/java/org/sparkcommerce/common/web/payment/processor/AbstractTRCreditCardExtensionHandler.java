/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */

package org.sparkcommerce.common.web.payment.processor;

import org.sparkcommerce.common.extension.AbstractExtensionHandler;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;
import org.sparkcommerce.common.payment.dto.PaymentRequestDTO;
import org.sparkcommerce.common.payment.dto.PaymentResponseDTO;
import org.sparkcommerce.common.payment.service.PaymentGatewayConfiguration;
import org.sparkcommerce.common.payment.service.PaymentGatewayTransparentRedirectService;
import org.sparkcommerce.common.vendor.service.exception.PaymentException;

import java.util.Map;

/**
 * <p>An Abstract implementation of the TRCreditCardExtensionHandler.
 * PaymentGateway Handlers will just need to extend this class and implement
 * the declated abstract methods.</p>
 *
 * @author Anand Dasari
 */
public abstract class AbstractTRCreditCardExtensionHandler extends AbstractExtensionHandler
        implements TRCreditCardExtensionHandler {

    @Override
    public ExtensionResultStatusType setFormActionKey(StringBuilder key) {
        key.delete(0, key.length());
        key.append(getFormActionURLKey());
        return ExtensionResultStatusType.HANDLED;
    }

    @Override
    public ExtensionResultStatusType setFormHiddenParamsKey(StringBuilder key) {
        key.delete(0, key.length());
        key.append(getHiddenParamsKey());
        return ExtensionResultStatusType.HANDLED;
    }

    @Override
    public ExtensionResultStatusType createTransparentRedirectForm(
            Map<String, Map<String, String>> formParameters,
            PaymentRequestDTO requestDTO,
            Map<String, String> configurationSettings) throws PaymentException {

        if (formParameters != null && requestDTO != null &&  configurationSettings != null) {
            //Populate any additional configs on the RequestDTO
            for (String config:configurationSettings.keySet()){
                requestDTO.additionalField(config, configurationSettings.get(config));
            }

            PaymentResponseDTO responseDTO;
            if (getConfiguration().isPerformAuthorizeAndCapture()) {
                responseDTO = getTransparentRedirectService().createAuthorizeAndCaptureForm(requestDTO);
            } else {
                responseDTO = getTransparentRedirectService().createAuthorizeForm(requestDTO);
            }

            populateFormParameters(formParameters, responseDTO);

        }

        return ExtensionResultStatusType.HANDLED_CONTINUE;
    }

    public abstract String getFormActionURLKey();

    public abstract String getHiddenParamsKey();

    public abstract PaymentGatewayConfiguration getConfiguration();

    public abstract PaymentGatewayTransparentRedirectService getTransparentRedirectService();

    public abstract void populateFormParameters(Map<String, Map<String, String>> formParameters,
                                                PaymentResponseDTO responseDTO);

}
