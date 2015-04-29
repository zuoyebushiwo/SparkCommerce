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
package org.sparkcommerce.core.catalog.domain;

import org.sparkcommerce.common.currency.domain.SparkCurrency;
import org.sparkcommerce.common.currency.domain.SparkCurrencyImpl;
import org.sparkcommerce.common.currency.util.SparkCurrencyUtils;
import org.sparkcommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import org.sparkcommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import org.sparkcommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import org.sparkcommerce.common.money.Money;
import org.sparkcommerce.common.presentation.AdminPresentation;
import org.sparkcommerce.common.presentation.client.SupportedFieldType;
import org.sparkcommerce.core.catalog.service.type.SkuFeeType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Phillip Verheyden
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="SC_SKU_FEE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blProducts")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps=true)
})
public class SkuFeeImpl implements SkuFee {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(generator = "SkuFeeId")
    @GenericGenerator(
        name = "SkuFeeId",
        strategy="org.sparkcommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="SkuFeeImpl"),
            @Parameter(name = "entity_name", value = "org.sparkcommerce.core.order.domain.SkuFeeImpl")
        }
    )
    @Column(name = "SKU_FEE_ID")
    protected Long id;

    @Column(name = "NAME")
    protected String name;
    
    @Column(name = "DESCRIPTION")
    protected String description;
    
    @Column(name ="AMOUNT", precision=19, scale=5, nullable=false)
    protected BigDecimal amount;
    
    @Column(name = "TAXABLE")
    protected Boolean taxable = Boolean.FALSE;
    
    @Lob
    @Type(type = "org.hibernate.type.StringClobType")
    @Column(name = "EXPRESSION", length = Integer.MAX_VALUE - 1)
    protected String expression;

    @Column(name = "FEE_TYPE")
    @AdminPresentation(fieldType=SupportedFieldType.BROADLEAF_ENUMERATION, sparkEnumeration="org.sparkcommerce.core.catalog.service.type.SkuFeeType")
    protected String feeType = SkuFeeType.FULFILLMENT.getType();
    
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = SkuImpl.class)
    @JoinTable(name = "SC_SKU_FEE_XREF",
            joinColumns = @JoinColumn(name = "SKU_FEE_ID", referencedColumnName = "SKU_FEE_ID", nullable = true),
            inverseJoinColumns = @JoinColumn(name = "SKU_ID", referencedColumnName = "SKU_ID", nullable = true))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
    protected List<Sku> skus;
    
    @ManyToOne(targetEntity = SparkCurrencyImpl.class)
    @JoinColumn(name = "CURRENCY_CODE")
    @AdminPresentation(friendlyName = "TaxDetailImpl_Currency_Code", order=1, group = "FixedPriceFulfillmentOptionImpl_Details", prominent=true)
    protected SparkCurrency currency;




    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Money getAmount() {
        return SparkCurrencyUtils.getMoney(amount, getCurrency());
    }

    @Override
    public void setAmount(Money amount) {
        this.amount = Money.toAmount(amount);
    }

    @Override
    public Boolean getTaxable() {
        return taxable;
    }

    @Override
    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }

    @Override
    public String getExpression() {
        return expression;
    }

    @Override
    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public SkuFeeType getFeeType() {
        return SkuFeeType.getInstance(feeType);
    }

    @Override
    public void setFeeType(SkuFeeType feeType) {
        this.feeType = (feeType == null) ? null : feeType.getType();
    }
    
    @Override
    public List<Sku> getSkus() {
        return skus;
    }
    
    @Override
    public void setSkus(List<Sku> skus) {
        this.skus = skus;
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
