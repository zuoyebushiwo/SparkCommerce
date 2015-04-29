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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sparkcommerce.common.web.SparkRequestContext;
import org.sparkcommerce.openadmin.dto.BasicFieldMetadata;
import org.sparkcommerce.openadmin.dto.Entity;
import org.sparkcommerce.openadmin.dto.FieldMetadata;
import org.sparkcommerce.openadmin.dto.Property;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;


/**
 * Makes a field required if the value of another field matches another value.
 * Designed for use where selecting a radio makes another field required.
 * 
 * This validator supports two approaches.   For both approaches, use compareField to indicate
 * the property name you want to compare ...
 * 
 * To compare against a specific value, also provide a "compareFieldValue" attribute.
 * 
 * To compare against a specific fieldName, also provide a "compareFieldName" attribute 
 * 
 * @author Brian Polster
 */
@Component("blRequiredIfPropertyValidator")
public class RequiredIfPropertyValidator extends ValidationConfigurationBasedPropertyValidator {

    protected static final Log LOG = LogFactory.getLog(RequiredIfPropertyValidator.class);

    @Override
    public PropertyValidationResult validate(Entity entity,
            Serializable instance,
            Map<String, FieldMetadata> entityFieldMetadata,
            Map<String, String> validationConfiguration,
            BasicFieldMetadata propertyMetadata,
            String propertyName,
            String value) {
        String errorMessage = "";

        String compareFieldName = lookupCompareFieldName(propertyName, validationConfiguration);
        String compareFieldValue = validationConfiguration.get("compareFieldValue");
        String compareFieldRegEx = validationConfiguration.get("compareFieldRegEx");
        Property compareFieldProperty = null;

        boolean valid = true;
        if (StringUtils.isEmpty(value)) {
            compareFieldProperty = entity.getPMap().get(compareFieldName);

            if (compareFieldProperty != null) {
                if (compareFieldValue != null) {
                    valid = !compareFieldValue.equals(compareFieldProperty.getValue());
                } else if (compareFieldRegEx != null) {
                    String expression = validationConfiguration.get("compareFieldRegEx");
                    valid = !compareFieldProperty.getValue().matches(expression);
                }

            }
        }

        if (!valid) {
            SparkRequestContext context = SparkRequestContext.getSparkRequestContext();
            MessageSource messages = context.getMessageSource();

            FieldMetadata fmd = entityFieldMetadata.get(compareFieldName);
            String fieldName = messages.getMessage(fmd.getFriendlyName(), null, context.getJavaLocale());
            errorMessage = messages.getMessage("requiredIfValidationFailure",
                    new Object[] { fieldName, compareFieldProperty.getValue() },
                    context.getJavaLocale());
        }

        return new PropertyValidationResult(valid, errorMessage);
    }

    protected String lookupCompareFieldName(String currentFieldName, Map<String, String> validationConfiguration) {
        String compareFieldName = validationConfiguration.get("compareField");
        if (currentFieldName.contains(".")) {
            String prefix = currentFieldName.substring(0, currentFieldName.lastIndexOf('.') + 1);
            return prefix + compareFieldName;
        } else {
            return compareFieldName;
        }
    }
}
