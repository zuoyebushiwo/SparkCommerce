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
package org.sparkcommerce.openadmin.server.service.persistence.module.criteria;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Jeff Fischer
 */
@Service("blRestrictionFactory")
public class RestrictionFactoryImpl implements RestrictionFactory {

    @Resource(name="blRestrictionFactoryMap")
    protected Map<String, Restriction> restrictions = new LinkedHashMap<String, Restriction>();

    public Map<String, Restriction> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(Map<String, Restriction> restrictions) {
        this.restrictions = restrictions;
    }

    @Override
    public Restriction getRestriction(String type, String propertyId) {
        Restriction restriction = restrictions.get(type).clone();
        restriction.setFieldPathBuilder(new FieldPathBuilder());

        return restriction;
    }
}
