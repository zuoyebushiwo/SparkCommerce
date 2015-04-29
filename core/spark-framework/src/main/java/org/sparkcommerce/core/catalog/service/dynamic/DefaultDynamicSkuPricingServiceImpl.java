/*
 * #%L
 * SparkCommerce Framework
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
package org.sparkcommerce.core.catalog.service.dynamic;

import org.sparkcommerce.common.money.Money;
import org.sparkcommerce.core.catalog.domain.ProductOptionValueImpl;
import org.sparkcommerce.core.catalog.domain.Sku;
import org.sparkcommerce.core.catalog.domain.SkuBundleItem;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Default implementation of the {@link DynamicSkuPricingService} which simply ignores the considerations hashmap in all
 * method implementations
 * 
 * @author jfischer
 * 
 */
@Service("blDynamicSkuPricingService")
public class DefaultDynamicSkuPricingServiceImpl implements DynamicSkuPricingService {

    @Override
    @SuppressWarnings("rawtypes")
    public DynamicSkuPrices getSkuPrices(Sku sku, HashMap skuPricingConsiderations) {
        DynamicSkuPrices prices = new DynamicSkuPrices();
        prices.setRetailPrice(sku.getRetailPrice());
        prices.setSalePrice(sku.getSalePrice());
        prices.setPriceAdjustment(sku.getProductOptionValueAdjustments());
        return prices;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public DynamicSkuPrices getSkuBundleItemPrice(SkuBundleItem skuBundleItem,
            HashMap skuPricingConsiderations) {
        DynamicSkuPrices prices = new DynamicSkuPrices();
        prices.setSalePrice(skuBundleItem.getSalePrice());
        return prices;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public DynamicSkuPrices getPriceAdjustment(ProductOptionValueImpl productOptionValueImpl, Money priceAdjustment,
            HashMap skuPricingConsiderationContext) {
        DynamicSkuPrices prices = new DynamicSkuPrices();

        prices.setPriceAdjustment(priceAdjustment);
        return prices;
    }

}
