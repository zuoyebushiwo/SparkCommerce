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
package org.sparkcommerce.openadmin.server.service.persistence.validation;

import org.sparkcommerce.common.presentation.ConfigurationItem;
import org.sparkcommerce.common.presentation.ValidationConfiguration;
import org.sparkcommerce.openadmin.dto.BasicFieldMetadata;
import org.sparkcommerce.openadmin.dto.Entity;
import org.sparkcommerce.openadmin.dto.FieldMetadata;

import java.io.Serializable;
import java.util.Map;


/**
 * Provides a default validate method that uses the validation configuration map to pull out the error key and pre-populate
 * the {@link PropertyValidationResult} based on {@link ConfigurationItem#ERROR_MESSAGE}.
 * 
 * This class should be used as your base if you are writing a validator based on a {@link ValidationConfiguration}
 *
 * @author Phillip Verheyden (phillipuniverse)
 */
public abstract class ValidationConfigurationBasedPropertyValidator implements PropertyValidator {

    @Override
    public PropertyValidationResult validate(Entity entity, Serializable instance, Map<String, FieldMetadata> entityFieldMetadata,
            Map<String, String> validationConfiguration,
            BasicFieldMetadata propertyMetadata,
            String propertyName,
            String value) {
        return new PropertyValidationResult(validateInternal(entity,
                instance,
                entityFieldMetadata,
                validationConfiguration,
                propertyMetadata,
                propertyName,
                value), validationConfiguration.get(ConfigurationItem.ERROR_MESSAGE));
    }
    
    /**
     * Delegate method for {@link ValidationConfiguration}-based processors that don't need to return an error message
     */
    public boolean validateInternal(Entity entity,
            Serializable instance,
            Map<String, FieldMetadata> entityFieldMetadata,
            Map<String, String> validationConfiguration,
            BasicFieldMetadata propertyMetadata,
            String propertyName,
            String value) {
        return false;
    }

    
}
