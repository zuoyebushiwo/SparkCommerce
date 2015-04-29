/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.currency.domain;


import org.sparkcommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import org.sparkcommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import org.sparkcommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import org.sparkcommerce.common.presentation.AdminPresentation;
import org.sparkcommerce.common.presentation.AdminPresentationClass;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Author: jerryocanas Date: 9/6/12
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "SC_CURRENCY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "blCMSElements")
@AdminPresentationClass(friendlyName = "SparkCurrencyImpl_baseCurrency")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITE)
})
public class SparkCurrencyImpl implements SparkCurrency {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CURRENCY_CODE")
    @AdminPresentation(friendlyName = "SparkCurrencyImpl_Currency_Code", order = 1, group = "SparkCurrencyImpl_Details", prominent = true)
    protected String currencyCode;

    @Column(name = "FRIENDLY_NAME")
    @AdminPresentation(friendlyName = "SparkCurrencyImpl_Name", order = 2, group = "SparkCurrencyImpl_Details", prominent = true)
    protected String friendlyName;

    @Column(name = "DEFAULT_FLAG")
    @AdminPresentation(friendlyName = "SparkCurrencyImpl_Is_Default", group = "SparkCurrencyImpl_Details", excluded = true)
    protected Boolean defaultFlag = false;

    @Override
    public String getCurrencyCode() {
        return currencyCode;
    }

    @Override
    public void setCurrencyCode(String code) {
        this.currencyCode = code;
    }

    @Override
    public String getFriendlyName() {
        return friendlyName;
    }

    @Override
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    @Override
    public boolean getDefaultFlag() {
        if (defaultFlag == null) {
            return false;
        }
        return defaultFlag.booleanValue();
    }

    @Override
    public void setDefaultFlag(boolean defaultFlag) {
        this.defaultFlag = new Boolean(defaultFlag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) {
            return false;
        }

        SparkCurrencyImpl currency = (SparkCurrencyImpl) o;

        if (currencyCode != null ? !currencyCode.equals(currency.currencyCode) : currency.currencyCode != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = currencyCode != null ? currencyCode.hashCode() : 0;
        return result;
    }
}
