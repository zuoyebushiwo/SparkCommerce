/*
 * #%L
 * SparkCommerce Integration
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
package org.sparkcommerce.common.currency;

import org.sparkcommerce.common.currency.domain.SparkCurrency;
import org.sparkcommerce.common.currency.domain.SparkCurrencyImpl;
import org.testng.annotations.DataProvider;


public class SparkCurrencyProvider {
  
    @DataProvider(name = "USCurrency")
    public static Object[][] provideUSCurrency() {
        SparkCurrency currency=new SparkCurrencyImpl();
        currency.setCurrencyCode("USD");
        currency.setDefaultFlag(true);
        currency.setFriendlyName("US Dollar");
        
        return new Object[][] { { currency } };
    }
    @DataProvider(name = "FRCurrency")
    public static Object[][] provideFRCurrency() {
        SparkCurrency currency=new SparkCurrencyImpl();
        currency.setCurrencyCode("EUR");
        currency.setDefaultFlag(true);
        currency.setFriendlyName("EURO Dollar");
        return new Object[][] { { currency } };
    }
}
