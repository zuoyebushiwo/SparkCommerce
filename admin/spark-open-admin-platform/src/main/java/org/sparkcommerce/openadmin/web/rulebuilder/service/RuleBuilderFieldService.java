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
package org.sparkcommerce.openadmin.web.rulebuilder.service;

import org.sparkcommerce.common.presentation.client.SupportedFieldType;
import org.sparkcommerce.openadmin.web.rulebuilder.dto.FieldDTO;
import org.sparkcommerce.openadmin.web.rulebuilder.dto.FieldData;
import org.sparkcommerce.openadmin.web.rulebuilder.dto.FieldWrapper;

import java.util.List;

/**
 * @author Elbert Bautista (elbertbautista)
 */
public interface RuleBuilderFieldService extends Cloneable {

    public String getName();

    public FieldWrapper buildFields();

    public FieldDTO getField(String fieldName);

    public SupportedFieldType getSupportedFieldType(String fieldName);

    public SupportedFieldType getSecondaryFieldType(String fieldName);

    public List<FieldData> getFields();

    public void setFields(List<FieldData> fields);

    public RuleBuilderFieldService clone() throws CloneNotSupportedException;

    public void setRuleBuilderFieldServiceExtensionManager(RuleBuilderFieldServiceExtensionManager extensionManager);
}
