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
package org.sparkcommerce.core.pricing.dao;

import org.sparkcommerce.core.order.domain.FulfillmentOption;
import org.sparkcommerce.core.order.fulfillment.domain.BandedPriceFulfillmentOption;
import org.sparkcommerce.core.pricing.domain.ShippingRate;
import org.sparkcommerce.core.pricing.service.FulfillmentPricingService;
import org.sparkcommerce.core.pricing.service.fulfillment.provider.BandedFulfillmentPricingProvider;

import java.math.BigDecimal;

/**
 * @deprecated Superceded in functionality by {@link BandedPriceFulfillmentOption} and {@link BandedFulfillmentPricingProvider}
 * @see {@link FulfillmentOption}, {@link FulfillmentPricingService}
 */
@Deprecated
public interface ShippingRateDao {

    public ShippingRate save(ShippingRate shippingRate);

    public ShippingRate readShippingRateById(Long id);

    public ShippingRate readShippingRateByFeeTypesUnityQty(String feeType, String feeSubType, BigDecimal unitQuantity);

    public ShippingRate create();

}
