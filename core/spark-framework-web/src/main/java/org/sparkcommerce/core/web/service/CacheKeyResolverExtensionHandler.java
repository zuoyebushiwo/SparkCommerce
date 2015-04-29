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
package org.sparkcommerce.core.web.service;

import org.sparkcommerce.common.extension.ExtensionHandler;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;


/**
 * @author bpolster
 */
public interface CacheKeyResolverExtensionHandler extends ExtensionHandler {

    /**
     * The passed in StringBuilder represents the current state of the cache key prior
     * to running any extension handlers.
     * 
     * Any implementations of this processor can read modify the passed in stringBuilder as
     * needed.    
     * 
     * @param stringBuilder
     * @param hasProducts
     * @return
     */
    public ExtensionResultStatusType updateCacheKey(StringBuilder stringBuilder, boolean hasProducts);
}
