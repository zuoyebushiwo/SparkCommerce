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
package org.sparkcommerce.core.web.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sparkcommerce.common.extension.ExtensionResultHolder;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;
import org.sparkcommerce.common.util.SCSystemProperty;
import org.sparkcommerce.common.web.SparkRequestContext;
import org.sparkcommerce.core.catalog.domain.ProductOptionValue;
import org.sparkcommerce.core.catalog.domain.Sku;
import org.sparkcommerce.core.catalog.service.CatalogService;
import org.sparkcommerce.core.order.domain.BundleOrderItem;
import org.sparkcommerce.core.order.domain.DiscreteOrderItem;
import org.sparkcommerce.core.order.domain.Order;
import org.sparkcommerce.core.order.domain.OrderItem;
import org.sparkcommerce.core.order.domain.OrderItemAttribute;
import org.sparkcommerce.core.order.domain.OrderItemAttributeImpl;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author bpolster
 */
@Service("bli18nUpdateCartServiceExtensionHandler")
public class i18nUpdateCartServiceExtensionHandler extends AbstractUpdateCartServiceExtensionHandler
        implements UpdateCartServiceExtensionHandler {

    protected static final Log LOG = LogFactory.getLog(i18nUpdateCartServiceExtensionHandler.class);

    protected boolean getClearCartOnLocaleSwitch() {
        return SCSystemProperty.resolveBooleanSystemProperty("clearCartOnLocaleSwitch");
    }

    @Resource(name = "blCatalogService")
    protected CatalogService catalogService;

    @Resource(name = "blUpdateCartServiceExtensionManager")
    protected UpdateCartServiceExtensionManager extensionManager;

    @PostConstruct
    public void init() {
        if (isEnabled()) {
            extensionManager.getHandlers().add(this);
        }
    }

    /**
     * If the locale of the cart does not match the current locale, then this extension handler will
     * attempt to translate the order items.  
     * 
     * The property "clearCartOnLocaleSwitch" can be set to true if the implementation desires to 
     * create a new cart when the locale is switched (3.0.6 and prior behavior).
     * 
     * @param cart
     * @param resultHolder
     * @return
     */
    public ExtensionResultStatusType updateAndValidateCart(Order cart, ExtensionResultHolder resultHolder) {
        if (SparkRequestContext.hasLocale()) {
            SparkRequestContext brc = SparkRequestContext.getSparkRequestContext();
            if (!brc.getLocale().getLocaleCode().matches(cart.getLocale().getLocaleCode())) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("The cart Locale [" + cart.getLocale().getLocaleCode() +
                            "] does not match the current locale [" + brc.getLocale().getLocaleCode() + "]");
                }

                if (getClearCartOnLocaleSwitch()) {
                    resultHolder.getContextMap().put("clearCart", Boolean.TRUE);
                } else {
                    fixTranslations(cart);
                    cart.setLocale(brc.getLocale());
                    resultHolder.getContextMap().put("saveCart", Boolean.TRUE);
                }
            }
        }
        return ExtensionResultStatusType.HANDLED_CONTINUE;
    }

    protected void fixTranslations(Order cart) {
        for (DiscreteOrderItem orderItem : cart.getDiscreteOrderItems()) {
            Sku sku = orderItem.getSku();
            translateOrderItem(orderItem, sku);
        }

        for (OrderItem orderItem : cart.getOrderItems()) {
            if (orderItem instanceof BundleOrderItem) {
                BundleOrderItem bundleItem = (BundleOrderItem) orderItem;
                Sku sku = bundleItem.getSku();
                translateOrderItem(orderItem, sku);
            }
        }
    }

    protected void translateOrderItem(OrderItem orderItem, Sku sku) {
        if (sku != null) {
            orderItem.setName(sku.getName());
            if (sku.getProductOptionValues() != null) {
                for (ProductOptionValue optionValue : sku.getProductOptionValues()) {
                    String key = optionValue.getProductOption().getAttributeName();
                    OrderItemAttribute attr = orderItem.getOrderItemAttributes().get(key);
                    if (attr != null) {
                        attr.setValue(optionValue.getAttributeValue());
                    } else {
                        OrderItemAttribute attribute = new OrderItemAttributeImpl();
                        attribute.setName(key);
                        attribute.setValue(optionValue.getAttributeValue());
                        attribute.setOrderItem(orderItem);
                        orderItem.getOrderItemAttributes().put(key, attribute);
                    }
                }
            }
        }
    }
}
