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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sparkcommerce.core.checkout.service.workflow.CheckoutSeed;
import org.sparkcommerce.core.workflow.Activity;
import org.sparkcommerce.core.workflow.ProcessContext;
import org.sparkcommerce.core.workflow.state.RollbackFailureException;
import org.sparkcommerce.core.workflow.state.RollbackHandler;
import org.sparkcommerce.core.workflow.state.RollbackStateLocal;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Demonstrate a call to a RollbackHandler
 *
 * @author Jeff Fischer
 */
public class TestRollbackHandler implements RollbackHandler<CheckoutSeed> {

    private static final Log LOG = LogFactory.getLog(TestRollbackHandler.class);

    @Override
    @Transactional("blTransactionManager")
    public void rollbackState(Activity<? extends ProcessContext<CheckoutSeed>> activity,
            ProcessContext<CheckoutSeed> processContext, Map<String, Object> stateConfiguration) throws RollbackFailureException {
        LOG.warn("******************* TestRollbackHandler Engaged *********************");
        LOG.warn("******************* Activity: " + activity.getBeanName() + " *********************");
        RollbackStateLocal rollbackStateLocal = RollbackStateLocal.getRollbackStateLocal();
        LOG.warn("******************* Workflow: " + rollbackStateLocal.getWorkflowId() + " *********************");
        LOG.warn("******************* Thread: " + rollbackStateLocal.getThreadId() + " *********************");
    }
}
