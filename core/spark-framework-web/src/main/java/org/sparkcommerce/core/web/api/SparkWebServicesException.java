/*
 * #%L
 * SparkCommerce Framework Web
 * %%
 * Copyright (C) 2009 - 2013 Spark Commerce
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
package org.sparkcommerce.core.web.api;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * The purpose of this is to allow REST endpoints to build an exception with 
 * @author Kelly Tisdell
 *
 */
public class SparkWebServicesException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String UNKNOWN_ERROR = "org.sparkcommerce.core.web.api.SparkWebServicesException.unknownError";
    public static final String PRODUCT_NOT_FOUND = "org.sparkcommerce.core.web.api.SparkWebServicesException.productNotFound";
    public static final String CATEGORY_NOT_FOUND = "org.sparkcommerce.core.web.api.SparkWebServicesException.categoryNotFound";
    public static final String SKU_NOT_FOUND = "org.sparkcommerce.core.web.api.SparkWebServicesException.skuNotFound";
    public static final String SEARCH_ERROR = "org.sparkcommerce.core.web.api.SparkWebServicesException.errorExecutingSearch";
    public static final String SEARCH_QUERY_EMPTY = "org.sparkcommerce.core.web.api.SparkWebServicesException.searchQueryEmpty";
    public static final String SEARCH_QUERY_MALFORMED = "org.sparkcommerce.core.web.api.SparkWebServicesException.searchQueryMalformed";
    public static final String INVALID_CATEGORY_ID = "org.sparkcommerce.core.web.api.SparkWebServicesException.invalidCategoryId";
    public static final String CART_NOT_FOUND = "org.sparkcommerce.core.web.api.SparkWebServicesException.cartNotFound";
    public static final String CART_ITEM_NOT_FOUND = "org.sparkcommerce.core.web.api.SparkWebServicesException.cartItemNotFound";
    public static final String CART_PRICING_ERROR = "org.sparkcommerce.core.web.api.SparkWebServicesException.cartPricingError";
    public static final String UPDATE_CART_ERROR = "org.sparkcommerce.core.web.api.SparkWebServicesException.updateCartError";
    public static final String PROMO_CODE_MAX_USAGES = "org.sparkcommerce.core.web.api.SparkWebServicesException.promoCodeMaxUsages";
    public static final String PROMO_CODE_INVALID = "org.sparkcommerce.core.web.api.SparkWebServicesException.promoCodeInvalid";
    public static final String FULFILLMENT_GROUP_NOT_FOUND = "org.sparkcommerce.core.web.api.SparkWebServicesException.fulfillmentGroupNotFound";
    public static final String FULFILLMENT_OPTION_NOT_FOUND = "org.sparkcommerce.core.web.api.SparkWebServicesException.fulfillmentOptionNotFound";
    public static final String CUSTOMER_NOT_FOUND = "org.sparkcommerce.core.web.api.SparkWebServicesException.customerNotFound";
    public static final String CHECKOUT_PROCESSING_ERROR = "org.sparkcommerce.core.web.api.SparkWebServicesException.checkoutProcessingError";

    protected int httpStatusCode = 500;

    protected Map<String, Object[]> messages;

    protected Map<String, String> translatedMessages;

    protected Locale locale;

    public SparkWebServicesException(int httpStatusCode, Locale locale, Map<String, Object[]> messages, Throwable cause) {
        super(cause);
        this.httpStatusCode = httpStatusCode;
        this.locale = locale;
        this.messages = messages;
    }

    public static SparkWebServicesException build(int httpStatusCode) {
        return build(httpStatusCode, null, null, null);
    }

    public static SparkWebServicesException build(int httpStatusCode, Throwable t) {
        return build(httpStatusCode, null, null, t);
    }

    public static SparkWebServicesException build(int httpStatusCode, Locale locale) {
        return build(httpStatusCode, locale, null, null);
    }

    public static SparkWebServicesException build(int httpStatusCode, Locale locale, Throwable t) {
        return build(httpStatusCode, locale, null, t);
    }

    public static SparkWebServicesException build(int httpStatusCode, Locale locale, Map<String, Object[]> messages) {
        return build(httpStatusCode, locale, messages, null);
    }

    public static SparkWebServicesException build(int httpStatusCode, Locale locale, Map<String, Object[]> messages, Throwable cause) {
        return new SparkWebServicesException(httpStatusCode, locale, messages, cause);
    }

    /**
     * Returns the intended HTTP status code (e.g. 400, 403, 404, 500, etc...). Default is 500.
     * @return
     */
    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    /**
     * Gets a map of message keys and object arrays. These will generally be used to build a 
     * an internationalized message in the response. The map key is typically going to be a 
     * message bundle key, and the object array provides parameters for the message.  For more information, 
     * see Spring's <code>org.springframework.context.MessageSource</code>.
     * @return
     */
    public Map<String, Object[]> getMessages() {
        if (this.messages == null) {
            this.messages = new HashMap<String, Object[]>();
        }
        return this.messages;
    }

    /**
     * Gets a map of messages that have already been translated. SC provides a number of translation mechanisms 
     * for entities in the DB.
     * 
     * @return
     */
    public Map<String,String> getTranslatedMessages() {
        if (this.translatedMessages == null) {
            this.translatedMessages = new HashMap<String, String>();
        }
        return this.translatedMessages;
    }

    /**
     * Adds a translated message. The assumption is that the message added to this map does not need to be 
     * translated or internationalized any further. It is already translated according to the user's Locale.
     * @param key
     * @param message
     * @return
     */
    public SparkWebServicesException addTranslatedMessage(String key, String message) {
        getTranslatedMessages().put(key, message);
        return this;
    }

    /**
     * Convenience method for adding a message with no parameters
     * @param key
     * @return
     */
    public SparkWebServicesException addMessage(String key) {
        return addMessage(key, null);
    }

    /**
     * Convenience method for adding a message with a single parameter
     * @param key
     * @param param
     * @return
     */
    public SparkWebServicesException addMessage(String key, Object param) {
        if (param != null) {
            return addMessage(key, new Object[] { param });
        } else {
            return addMessage(key, new Object[0]);
        }
    }

    /**
     * Convenience mentod for adding a message with  multiple parameters
     * @param key
     * @param params
     * @return
     */
    public SparkWebServicesException addMessage(String key, Object[] params) {
        getMessages().put(key, params);
        return this;
    }

    /**
     * Returns the <code>java.util.Locale</code> that messages should be formatted in.
     * @return
     */
    public Locale getLocale() {
        return this.locale;
    }
}
