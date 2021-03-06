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
package org.sparkcommerce.core.order.domain;

import org.sparkcommerce.common.money.Money;

import java.io.Serializable;

public interface BundleOrderItemFeePrice extends Serializable {

    public abstract Long getId();

    public abstract void setId(Long id);

    public abstract BundleOrderItem getBundleOrderItem();

    public abstract void setBundleOrderItem(BundleOrderItem bundleOrderItem);

    public abstract Money getAmount();

    public abstract void setAmount(Money amount);

    public abstract String getName();

    public abstract void setName(String name);

    public abstract Boolean isTaxable();

    public abstract void setTaxable(Boolean isTaxable);

    public abstract String getReportingCode();

    public abstract void setReportingCode(String reportingCode);

    public BundleOrderItemFeePrice clone();

}
