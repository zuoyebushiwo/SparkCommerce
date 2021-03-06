/*
 * #%L
 * SparkCommerce CMS Module
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
package org.sparkcommerce.cms.field.domain;

import org.sparkcommerce.common.enumeration.domain.DataDrivenEnumeration;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jfischer
 * @deprecated use {@link DataDrivenEnumeration} instead
 */
@Deprecated
public interface FieldEnumeration extends Serializable {

    @Deprecated
    List<FieldEnumerationItem> getEnumerationItems();

    @Deprecated
    void setEnumerationItems(List<FieldEnumerationItem> enumerationItems);

    @Deprecated
    Long getId();

    @Deprecated
    void setId(Long id);

    @Deprecated
    String getName();

    @Deprecated
    void setName(String name);
}
