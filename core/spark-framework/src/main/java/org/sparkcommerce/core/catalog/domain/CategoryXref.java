/*
 * #%L
 * SparkCommerce Framework
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
package org.sparkcommerce.core.catalog.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public interface CategoryXref extends Serializable {

    public BigDecimal getDisplayOrder();

    public void setDisplayOrder(final BigDecimal displayOrder);
    
    public Category getCategory();

    public void setCategory(final Category category);

    public Category getSubCategory();

    public void setSubCategory(final Category subCategory);

    public void setId(Long id);

    public Long getId();

}