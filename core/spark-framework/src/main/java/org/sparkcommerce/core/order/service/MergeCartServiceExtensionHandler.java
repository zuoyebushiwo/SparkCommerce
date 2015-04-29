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
package org.sparkcommerce.core.order.service;

import org.sparkcommerce.common.extension.ExtensionHandler;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;
import org.sparkcommerce.core.order.domain.Order;
import org.sparkcommerce.profile.core.domain.Customer;


/**
 * Extension handler for merge cart
 * 
 * @author Andre Azzolini (apazzolini)
 */
public interface MergeCartServiceExtensionHandler extends ExtensionHandler {
    
    ExtensionResultStatusType setNewCartOwnership(Order cart, Customer customer);

    ExtensionResultStatusType updateMergedOrder(Order cart, Customer customer);
    
}
