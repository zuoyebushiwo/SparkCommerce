/*
 * #%L
 * SparkCommerce Open Admin Platform
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
package org.sparkcommerce.openadmin.server.service.persistence.module.provider;

import org.sparkcommerce.common.currency.util.SparkCurrencyUtils;
import org.sparkcommerce.openadmin.dto.Property;
import org.sparkcommerce.openadmin.server.service.persistence.PersistenceException;
import org.sparkcommerce.openadmin.server.service.persistence.module.provider.request.ExtractValueRequest;
import org.sparkcommerce.openadmin.server.service.type.FieldProviderResponse;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * Abstract persistence provider that provides a method to actually handle formatting moneys.
 * 
 * @author Andre Azzolini (apazzolini)
 */
public abstract class AbstractMoneyFieldPersistenceProvider extends FieldPersistenceProviderAdapter {
    
    @Override
    public FieldProviderResponse extractValue(ExtractValueRequest extractValueRequest, Property property) throws PersistenceException {
        if (!canHandleExtraction(extractValueRequest, property)) {
            return FieldProviderResponse.NOT_HANDLED;
        }
        
        if (extractValueRequest.getRequestedValue() == null) {
            return FieldProviderResponse.NOT_HANDLED;
        }
        
        property.setValue(formatValue((BigDecimal)extractValueRequest.getRequestedValue(), extractValueRequest, property));
        property.setDisplayValue(formatDisplayValue((BigDecimal)extractValueRequest.getRequestedValue(), extractValueRequest, property));
        
        return FieldProviderResponse.HANDLED_BREAK;
    }
    
    protected String formatValue(BigDecimal value, ExtractValueRequest extractValueRequest, Property property) {
        NumberFormat format = NumberFormat.getInstance(getLocale(extractValueRequest, property));
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        format.setGroupingUsed(false);
        return format.format(value);
    }
    
    protected String formatDisplayValue(BigDecimal value, ExtractValueRequest extractValueRequest, Property property) {
        Locale locale = getLocale(extractValueRequest, property);
        Currency currency = getCurrency(extractValueRequest, property);
        return SparkCurrencyUtils.getNumberFormatFromCache(locale, currency).format(value);
    }
    
    protected abstract boolean canHandleExtraction(ExtractValueRequest extractValueRequest, Property property);
    
    protected abstract Locale getLocale(ExtractValueRequest extractValueRequest, Property property);
    
    protected abstract Currency getCurrency(ExtractValueRequest extractValueRequest, Property property);
    
}
