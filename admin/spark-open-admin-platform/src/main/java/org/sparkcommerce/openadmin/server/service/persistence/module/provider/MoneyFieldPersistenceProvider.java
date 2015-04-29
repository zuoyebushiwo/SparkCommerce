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

import org.apache.commons.lang3.StringUtils;
import org.sparkcommerce.common.currency.util.CurrencyCodeIdentifiable;
import org.sparkcommerce.common.presentation.client.SupportedFieldType;
import org.sparkcommerce.common.web.SparkRequestContext;
import org.sparkcommerce.openadmin.dto.Property;
import org.sparkcommerce.openadmin.server.service.persistence.module.provider.request.ExtractValueRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Currency;
import java.util.Locale;

/**
 * Persistence provider capable of extracting friendly display values for Money fields
 * 
 * @author Andre Azzolini (apazzolini)
 */
@Scope("prototype")
@Component("blMoneyFieldPersistenceProvider")
public class MoneyFieldPersistenceProvider extends AbstractMoneyFieldPersistenceProvider {
    
    @Override
    public int getOrder() {
        return FieldPersistenceProvider.MONEY;
    }
    
    @Override
    protected boolean canHandleExtraction(ExtractValueRequest extractValueRequest, Property property) {
        return extractValueRequest.getMetadata().getFieldType() == SupportedFieldType.MONEY;
    }
    
    @Override
    protected Locale getLocale(ExtractValueRequest extractValueRequest, Property property) {
        SparkRequestContext brc = SparkRequestContext.getSparkRequestContext();
        return brc.getJavaLocale();
    }

    @Override
    protected Currency getCurrency(ExtractValueRequest extractValueRequest, Property property) {
        String currencyCodeField = extractValueRequest.getMetadata().getCurrencyCodeField();
        if (!StringUtils.isEmpty(currencyCodeField)) {
            try {
                return Currency.getInstance((String) extractValueRequest.getFieldManager().getFieldValue(extractValueRequest.getEntity(), currencyCodeField));
            } catch (Exception e) {
                //do nothing
            }
        }
        if (extractValueRequest.getEntity() instanceof CurrencyCodeIdentifiable) {
            try {
                return Currency.getInstance(((CurrencyCodeIdentifiable) extractValueRequest.getEntity()).getCurrencyCode());
            } catch (Exception e) {
                //do nothing
            }
        }
        SparkRequestContext brc = SparkRequestContext.getSparkRequestContext();
        return brc.getJavaCurrency();
    }
    
}
