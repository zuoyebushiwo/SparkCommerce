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
package org.sparkcommerce.core.web.order.security.extension;

import org.sparkcommerce.common.extension.AbstractExtensionHandler;
import org.sparkcommerce.common.extension.ExtensionResultStatusType;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Abstract handler for {@link AuthSuccessHandlerExtensionHandler} so that actual implementations of this handler
 * do not need to implemenet every single method.
 * 
 * @author Andre Azzolini (apazzolini)
 */
public class AbstractAuthSuccessHandlerExtensionHandler extends AbstractExtensionHandler implements 
    AuthSuccessHandlerExtensionHandler {

    @Override
    public ExtensionResultStatusType preMergeCartExecution(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
