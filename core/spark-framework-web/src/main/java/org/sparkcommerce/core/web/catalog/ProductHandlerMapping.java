/*
 * #%L
 * SparkCommerce Framework Web
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
package org.sparkcommerce.core.web.catalog;

import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.sparkcommerce.common.web.SCAbstractHandlerMapping;
import org.sparkcommerce.common.web.SparkRequestContext;
import org.sparkcommerce.core.catalog.domain.Product;
import org.sparkcommerce.core.catalog.service.CatalogService;
import org.springframework.beans.factory.annotation.Value;

/**
 * This handler mapping works with the Category entity to determine if a category has been configured for
 * the passed in URL.   
 * 
 * If the URL matches a valid Category then this mapping returns the handler configured via the 
 * controllerName property or blCategoryController by default. 
 *
 * @author bpolster
 * @since 2.0
 * @see org.sparkcommerce.core.catalog.domain.Product
 * @see CatalogService
 */
public class ProductHandlerMapping extends SCAbstractHandlerMapping {
    
    private String controllerName="blProductController";

    @Resource(name = "blCatalogService")
    private CatalogService catalogService;

    protected String defaultTemplateName = "catalog/product";

    public static final String CURRENT_PRODUCT_ATTRIBUTE_NAME = "currentProduct";

    @Value("${request.uri.encoding}")
    public String charEncoding;

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
        SparkRequestContext context = SparkRequestContext.getSparkRequestContext();
        if (context != null && context.getRequestURIWithoutContext() != null) {
            String requestUri = URLDecoder.decode(context.getRequestURIWithoutContext(), charEncoding);
            Product product = catalogService.findProductByURI(requestUri);
            if (product != null) {
                context.getRequest().setAttribute(CURRENT_PRODUCT_ATTRIBUTE_NAME, product);
                return controllerName;
            }
        }
        return null;
    }

    public String getDefaultTemplateName() {
        return defaultTemplateName;
    }

    public void setDefaultTemplateName(String defaultTemplateName) {
        this.defaultTemplateName = defaultTemplateName;
    }

}
