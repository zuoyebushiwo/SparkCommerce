/*
 * #%L
 * SparkCommerce Integration
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
package org.sparkcommerce.core.workflow.state.test;

import org.sparkcommerce.core.workflow.BaseActivity;
import org.sparkcommerce.core.workflow.ProcessContext;

/**
 * Create an activity that simply throws an exception in order to engage the rollback behavior for the workflow
 *
 * @author Jeff Fischer
 */
public class TestRollbackActivity extends BaseActivity<ProcessContext<? extends Object>> {

    @Override
    public ProcessContext<? extends Object> execute(ProcessContext<? extends Object> context) throws Exception {
        throw new IllegalArgumentException("TestRollbackActivity Accessed...");
    }

}
