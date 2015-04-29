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
package org.sparkcommerce.core.pricing.service.module;

import org.sparkcommerce.core.order.domain.Order;
import org.sparkcommerce.core.pricing.service.TaxService;
import org.sparkcommerce.core.pricing.service.exception.TaxException;
import org.sparkcommerce.core.pricing.service.tax.provider.TaxProvider;

/**
 * @deprectated use {@link TaxService} and {@link TaxProvider}s instead
 */
@Deprecated
public interface TaxModule {
    
    public String getName();
    
    public void setName(String name);
    
    public Order calculateTaxForOrder(Order order) throws TaxException;
    
}
