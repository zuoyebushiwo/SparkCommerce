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
package org.sparkcommerce.core.offer.service.workflow;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.sparkcommerce.core.checkout.service.workflow.CheckoutSeed;
import org.sparkcommerce.core.offer.domain.OfferAudit;
import org.sparkcommerce.core.offer.service.OfferAuditService;
import org.sparkcommerce.core.workflow.Activity;
import org.sparkcommerce.core.workflow.ProcessContext;
import org.sparkcommerce.core.workflow.state.RollbackFailureException;
import org.sparkcommerce.core.workflow.state.RollbackHandler;
import org.springframework.stereotype.Component;


/**
 * Rolls back audits that were saved in the database from {@link RecordOfferUsageActivity}.
 *
 * @author Phillip Verheyden (phillipuniverse)
 * @see {@link RecordOfferUsageActivity}
 */
@Component("blRecordOfferUsageRollbackHandler")
public class RecordOfferUsageRollbackHandler implements RollbackHandler<CheckoutSeed> {

    @Resource(name = "blOfferAuditService")
    protected OfferAuditService offerAuditService;
    
    @Override
    public void rollbackState(Activity<? extends ProcessContext<CheckoutSeed>> activity, ProcessContext<CheckoutSeed> processContext, Map<String, Object> stateConfiguration) throws RollbackFailureException {
        List<OfferAudit> audits = (List<OfferAudit>) stateConfiguration.get(RecordOfferUsageActivity.SAVED_AUDITS);
        
        for (OfferAudit audit : audits) {
            offerAuditService.delete(audit);
        }
    }
    
}
