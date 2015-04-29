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

import org.sparkcommerce.common.currency.domain.SparkCurrency;
import org.sparkcommerce.common.currency.domain.SparkCurrencyImpl;
import org.sparkcommerce.common.currency.util.SparkCurrencyUtils;
import org.sparkcommerce.common.money.Money;
import org.sparkcommerce.common.presentation.AdminPresentation;
import org.sparkcommerce.common.presentation.AdminPresentationClass;
import org.sparkcommerce.core.order.domain.FulfillmentOptionImpl;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 
 * @author Phillip Verheyden
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "SC_FULFILLMENT_OPTION_FIXED")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blStandardElements")
@AdminPresentationClass(friendlyName = "Fixed Price Fulfillment")
public class FixedPriceFulfillmentOptionImpl extends FulfillmentOptionImpl implements FixedPriceFulfillmentOption {

    private static final long serialVersionUID = 1L;

    @Column(name = "PRICE", precision=19, scale=5, nullable=false)
    protected BigDecimal price;
    
    @ManyToOne(targetEntity = SparkCurrencyImpl.class)
    @JoinColumn(name = "CURRENCY_CODE")
    @AdminPresentation(excluded = true)
    protected SparkCurrency currency;

    @Override
    public Money getPrice() {
        return price == null ? null : SparkCurrencyUtils.getMoney(price, getCurrency());
    }

    @Override
    public void setPrice(Money price) {
        this.price = Money.toAmount(price);
    }

    @Override
    public SparkCurrency getCurrency() {
        return currency;
    }

    @Override
    public void setCurrency(SparkCurrency currency) {
        this.currency = currency;
    }

}
