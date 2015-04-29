/*
 * #%L
 * SparkCommerce Framework
 * %%
 * Copyright (C) 2009 - 2014 Spark Commerce
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
package org.sparkcommerce.core.catalog.extension;

import org.sparkcommerce.common.extension.ExtensionHandler;
import org.sparkcommerce.common.extension.ExtensionResultHolder;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;
import org.sparkcommerce.core.catalog.domain.Product;

/**
 * For internal usage. Allows extending API calls without subclassing the entity.
 *
 * @author Jeff Fischer
 */
public interface ProductEntityExtensionHandler extends ExtensionHandler {

    ExtensionResultStatusType getAllParentCategoryXrefs(Product delegate, ExtensionResultHolder resultHolder);

    ExtensionResultStatusType getAllParentCategories(Product delegate, ExtensionResultHolder resultHolder);

    ExtensionResultStatusType getProductOptionXrefs(Product delegate, ExtensionResultHolder resultHolder);

    ExtensionResultStatusType getProductOptions(Product delegate, ExtensionResultHolder resultHolder);

    ExtensionResultStatusType getCrossSaleProducts(Product delegate, ExtensionResultHolder resultHolder);

    ExtensionResultStatusType getUpSaleProducts(Product delegate, ExtensionResultHolder resultHolder);

}
