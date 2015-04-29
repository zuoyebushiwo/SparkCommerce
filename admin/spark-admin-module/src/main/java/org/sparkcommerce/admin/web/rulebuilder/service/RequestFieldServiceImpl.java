/*
 * #%L
 * SparkCommerce Admin Module
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
package org.sparkcommerce.admin.web.rulebuilder.service;

import org.sparkcommerce.common.presentation.RuleIdentifier;
import org.sparkcommerce.common.presentation.client.SupportedFieldType;
import org.sparkcommerce.openadmin.web.rulebuilder.dto.FieldData;
import org.sparkcommerce.openadmin.web.rulebuilder.service.AbstractRuleBuilderFieldService;
import org.springframework.stereotype.Service;

/**
 * An implementation of a RuleBuilderFieldService
 * that constructs metadata necessary
 * to build the supported fields for a Request entity
 *
 * @author Andre Azzolini (apazzolini)
 */
@Service("blRequestFieldService")
public class RequestFieldServiceImpl extends AbstractRuleBuilderFieldService {

    @Override
    public void init() {
        fields.add(new FieldData.Builder()
                .label("rule_requestFullUrl")
                .name("fullUrlWithQueryString")
                .operators("blcOperators_Text")
                .options("[]")
                .type(SupportedFieldType.STRING)
                .build());
        
        fields.add(new FieldData.Builder()
                .label("rule_requestUri")
                .name("requestURI")
                .operators("blcOperators_Text")
                .options("[]")
                .type(SupportedFieldType.STRING)
                .build());
        
        fields.add(new FieldData.Builder()
                .label("rule_requestIsSecure")
                .name("secure")
                .operators("blcOperators_Boolean")
                .options("[]")
                .type(SupportedFieldType.BOOLEAN)
                .build());
    }

    @Override
    public String getName() {
        return RuleIdentifier.REQUEST;
    }

    @Override
    public String getDtoClassName() {
        return "org.sparkcommerce.common.RequestDTOImpl";
    }
}
