/*
 * #%L
 * SparkCommerce Open Admin Platform
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
package org.sparkcommerce.openadmin.server.service.persistence.module.criteria;

import org.sparkcommerce.openadmin.server.service.persistence.module.provider.FieldPersistenceProvider;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Event handler for adding restrictions to criteria created for admin fetch requests
 *
 * @author Jeff Fischer
 */
public interface CriteriaTranslatorEventHandler {

    /**
     * <p>
     * Allows the ability to attach additional criteria to the given <b>criteria</b> that has already been created by the
     * given <b>filterMappings</b>. Since translation has already occurred from the <b>filterMappings</b> into the given
     * <b>restrictions</b>, implementers should attach additional criteria to that list rather than attach to <b>criteria</b>
     * directly.
     * 
     * <p>
     * These 
     * 
     * @param ceilingEntity the entity currently being fetched
     * @param filterMappings the DTO of filters harvested from {@link FieldPersistenceProvider}s
     * @param criteriaBuilder used for adding additional restrictions
     * @param original the Hibernate root from which restriction paths start from
     * @param restrictions existing list of restrictions that have not yet been applied to <b>criteria</b>
     * @param sorts list of sorts that have not yet been applied to <b>criteria>
     * @param criteria the final criteria without any of the <b>restrictions</b> or <b>sorts</b> applied to it. Additional
     * {@link Predicate}s should not be attached to this directly but rather to the given <b>restrictions</b> or <b>sorts</b>
     * @see {@link CriteriaTranslatorImpl#addRestrictions(String, List, CriteriaBuilder, Root, List, List, CriteriaQuery)}
     */
    void addRestrictions(String ceilingEntity, List<FilterMapping> filterMappings, CriteriaBuilder criteriaBuilder,
                         Root original, List<Predicate> restrictions, List<Order> sorts, CriteriaQuery criteria);

}
