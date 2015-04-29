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
package org.sparkcommerce.admin.server.service.extension;

import org.sparkcommerce.common.extension.ExtensionHandler;
import org.sparkcommerce.common.extension.ExtensionResultHolder;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;
import org.sparkcommerce.core.catalog.domain.Product;
import org.sparkcommerce.core.catalog.domain.ProductOptionValue;

import java.util.List;


/**
 * Extension handler for {@link org.sparkcommerce.admin.server.service.AdminCatalogService}
 *
 * @author Jeff Fischer
 */
public interface AdminCatalogServiceExtensionHandler extends ExtensionHandler {

    public static final int DEFAULT_PRIORITY = 1000;

    /**
     * Customize the persistence of generated sku permutations based on product options.
     *
     * @param product
     * @param permutationsToGenerate
     * @param erh
     * @return
     */
    ExtensionResultStatusType persistSkuPermutation(Product product, List<List<ProductOptionValue>> permutationsToGenerate, ExtensionResultHolder<Integer> erh);

}
