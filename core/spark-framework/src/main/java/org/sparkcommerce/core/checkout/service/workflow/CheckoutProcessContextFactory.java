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
package org.sparkcommerce.core.checkout.service.workflow;

import org.sparkcommerce.core.workflow.DefaultProcessContextImpl;
import org.sparkcommerce.core.workflow.ProcessContext;
import org.sparkcommerce.core.workflow.ProcessContextFactory;
import org.sparkcommerce.core.workflow.WorkflowException;

public class CheckoutProcessContextFactory implements ProcessContextFactory<CheckoutSeed, CheckoutSeed> {

    @Override
    public ProcessContext<CheckoutSeed> createContext(CheckoutSeed seedData) throws WorkflowException {
        ProcessContext<CheckoutSeed> context = new DefaultProcessContextImpl<CheckoutSeed>();
        context.setSeedData(seedData);

        return context;
    }

}
