/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sparkcommerce.common.currency.domain.SparkCurrency;
import org.sparkcommerce.common.currency.service.SparkCurrencyService;
import org.sparkcommerce.common.locale.domain.Locale;
import org.sparkcommerce.common.util.SCRequestUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Anand Dasari
 * @Date: 9/5/15
 */

/**
 * Responsible for returning the currency to use for the current request.
 */
@Component("blCurrencyResolver")
public class SparkCurrencyResolverImpl implements SparkCurrencyResolver {

    private final Log LOG = LogFactory.getLog(SparkCurrencyResolverImpl.class);

    /**
     * Parameter/Attribute name for the current currency code
     */
    public static String CURRENCY_CODE_PARAM = "blCurrencyCode";

    /**
     * Parameter/Attribute name for the current currency
     */
    public static String CURRENCY_VAR = "blCurrency";

    @Resource(name = "blCurrencyService")
    private SparkCurrencyService sparkCurrencyService;

    /**
     * Responsible for returning the currency to use for the current request.
     */
    @Override
    public SparkCurrency resolveCurrency(HttpServletRequest request) {
        return resolveCurrency(new ServletWebRequest(request));
    }

    @Override
    public SparkCurrency resolveCurrency(WebRequest request) {
        SparkCurrency currency = null;

        // 1) Check request for currency
        currency = (SparkCurrency) request.getAttribute(CURRENCY_VAR, WebRequest.SCOPE_REQUEST);

        // 2) Check for a request parameter
        if (currency == null && SCRequestUtils.getURLorHeaderParameter(request, CURRENCY_CODE_PARAM) != null) {
            String currencyCode = SCRequestUtils.getURLorHeaderParameter(request, CURRENCY_CODE_PARAM);
            currency = sparkCurrencyService.findCurrencyByCode(currencyCode);
            if (LOG.isTraceEnabled()) {
                LOG.trace("Attempt to find currency by param " + currencyCode + " resulted in " + currency);
            }
        }

        // 3) Check session for currency
        if (currency == null && SCRequestUtils.isOKtoUseSession(request)) {
            currency = (SparkCurrency) request.getAttribute(CURRENCY_VAR, WebRequest.SCOPE_GLOBAL_SESSION);
        }

        // 4) Check locale for currency
        if (currency == null) {
            Locale locale = (Locale) request.getAttribute(SparkLocaleResolverImpl.LOCALE_VAR, WebRequest.SCOPE_REQUEST);
            if (locale != null) {
                currency = locale.getDefaultCurrency();
            }
        }

        // 5) Check default currency from DB
        if (currency == null) {
            currency = sparkCurrencyService.findDefaultSparkCurrency();
        }

        if (SCRequestUtils.isOKtoUseSession(request)) {
            request.setAttribute(CURRENCY_VAR, currency, WebRequest.SCOPE_GLOBAL_SESSION);
        }
        return currency;
    }



}
