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
package org.sparkcommerce.core.offer.dao;

import org.sparkcommerce.core.offer.domain.OfferAudit;
import org.sparkcommerce.core.offer.service.OfferService;
import org.sparkcommerce.core.offer.service.workflow.RecordOfferUsageActivity;
import org.sparkcommerce.core.offer.service.workflow.VerifyCustomerMaxOfferUsesActivity;

/**
 * DAO for auditing what went on with offers being added to an order
 *
 * @author Phillip Verheyden (phillipuniverse)
 * @see {@link VerifyCustomerMaxOfferUsesActivity}, {@link RecordOfferUsageActivity},
 * {@link OfferService#verifyMaxCustomerUsageThreshold(org.sparkcommerce.profile.core.domain.Customer, org.sparkcommerce.core.offer.domain.OfferCode)}
 */
public interface OfferAuditDao {
    
    public OfferAudit readAuditById(Long offerAuditId);
    
    /**
     * Persists an audit record to the database
     */
    public OfferAudit save(OfferAudit offerAudit);
    
    public void delete(OfferAudit offerAudit);

    /**
     * Creates a new offer audit
     */
    public OfferAudit create();
    
    /**
     * Counts how many times the an offer has been used by a customer
     * 
     * @param customerId
     * @param offerId
     * @return
     */
    public Long countUsesByCustomer(Long customerId, Long offerId);

    /**
     * Counts how many times the given offer code has been used in the system
     * 
     * @param offerCodeId
     * @return
     */
    public Long countOfferCodeUses(Long offerCodeId);

}
