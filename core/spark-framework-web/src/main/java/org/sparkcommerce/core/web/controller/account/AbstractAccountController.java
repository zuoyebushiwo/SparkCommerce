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
package org.sparkcommerce.core.web.controller.account;

import javax.annotation.Resource;

import org.sparkcommerce.common.web.controller.SparkAbstractController;
import org.sparkcommerce.core.catalog.service.CatalogService;
import org.sparkcommerce.core.order.service.OrderService;


/**
 * An abstract controller that provides convenience methods and resource declarations for its children. 
 * 
 * Operations that are shared between controllers that deal with the cart belong here.
 * 
 * @author apazzolini
 */
public abstract class AbstractAccountController extends SparkAbstractController {
    
    @Resource(name="blOrderService")
    protected OrderService orderService;

    @Resource(name="blCatalogService")
    protected CatalogService catalogService;
    
}
