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

import java.io.Serializable;

import org.sparkcommerce.openadmin.dto.Property;
import org.sparkcommerce.openadmin.server.service.persistence.PersistenceException;
import org.sparkcommerce.openadmin.server.service.persistence.module.provider.request.ExtractValueRequest;
import org.sparkcommerce.openadmin.server.service.persistence.module.provider.request.PopulateValueRequest;
import org.sparkcommerce.openadmin.server.service.type.FieldProviderResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Jeff Fischer
 */
@Component("blDefaultFieldPersistenceProvider")
@Scope("prototype")
public class DefaultFieldPersistenceProvider extends FieldPersistenceProviderAdapter {

    @Override
    public FieldProviderResponse populateValue(PopulateValueRequest populateValueRequest, Serializable instance) throws PersistenceException {
        boolean dirty;
        try {
            Property p = populateValueRequest.getProperty();
            Object value = populateValueRequest.getFieldManager().getFieldValue(instance, p.getName());

            if (value != null) {
                p.setOriginalValue(String.valueOf(value));
                p.setOriginalDisplayValue(p.getOriginalValue());
            }

            dirty = checkDirtyState(populateValueRequest, instance, populateValueRequest.getRequestedValue());
            populateValueRequest.getFieldManager().setFieldValue(instance,
                    populateValueRequest.getProperty().getName(), populateValueRequest.getRequestedValue());
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
        populateValueRequest.getProperty().setIsDirty(dirty);
        return FieldProviderResponse.HANDLED;
    }

    @Override
    public FieldProviderResponse extractValue(ExtractValueRequest extractValueRequest, Property property) throws PersistenceException {
        if (extractValueRequest.getRequestedValue() != null) {
            String val = extractValueRequest.getRequestedValue().toString();
            property.setValue(val);
            property.setDisplayValue(extractValueRequest.getDisplayVal());
        }
        return FieldProviderResponse.HANDLED;
    }

}
