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
package org.sparkcommerce.core.web.resolver;

import org.sparkcommerce.common.extension.ExtensionHandler;
import org.sparkcommerce.common.extension.ExtensionResultHolder;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;
import org.thymeleaf.TemplateProcessingParameters;

import java.io.InputStream;


/**
 * Extension handler for resolving templates from the database.
 * 
 * @author Andre Azzolini (apazzolini), bpolster
 */
public interface DatabaseResourceResolverExtensionHandler extends ExtensionHandler {
    
    public static final String IS_KEY = "IS_KEY";
    
    /**
     * If this method returns any of the handled conditions in {@link ExtensionResultStatusType},
     * the value keyed by {@link DatabaseResourceResolverExtensionHandler.IS_KEY} in the 
     * {@link ExtensionResultHolder}'s context map will be an {@link InputStream} of the resolved resource's
     * contents.
     * 
     * @param erh
     * @param params
     * @param resourceName
     * @return whether or not a resource was resolved
     */
    public ExtensionResultStatusType resolveResource(ExtensionResultHolder erh, 
            TemplateProcessingParameters params, String resourceName);

}