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
package org.sparkcommerce.core.order.fulfillment.domain;

import org.sparkcommerce.common.presentation.AdminPresentationClass;
import org.sparkcommerce.core.order.domain.FulfillmentOptionImpl;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * 
 * @author Phillip Verheyden
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "SC_FULFILLMENT_OPT_BANDED_PRC")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blStandardElements")
@AdminPresentationClass(friendlyName = "Banded Price Fulfillment Option")
public class BandedPriceFulfillmentOptionImpl extends FulfillmentOptionImpl implements BandedPriceFulfillmentOption {

    private static final long serialVersionUID = 1L;
    
    @OneToMany(mappedBy="option", targetEntity=FulfillmentPriceBandImpl.class)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
    protected List<FulfillmentPriceBand> bands;

    @Override
    public List<FulfillmentPriceBand> getBands() {
        return bands;
    }

    @Override
    public void setBands(List<FulfillmentPriceBand> bands) {
        this.bands = bands;
    }

}
