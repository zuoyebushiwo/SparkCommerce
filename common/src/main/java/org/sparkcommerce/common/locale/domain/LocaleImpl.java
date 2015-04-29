/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.locale.domain;

import org.sparkcommerce.common.currency.domain.SparkCurrency;
import org.sparkcommerce.common.currency.domain.SparkCurrencyImpl;
import org.sparkcommerce.common.presentation.AdminPresentation;
import org.sparkcommerce.common.presentation.AdminPresentationClass;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author : adasari
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "SC_LOCALE")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blCMSElements")
@AdminPresentationClass(friendlyName = "LocaleImpl_baseLocale")
public class LocaleImpl implements Locale {

    private static final long serialVersionUID = 1L;

    @Id
    @Column (name = "LOCALE_CODE")
    @AdminPresentation(friendlyName = "LocaleImpl_Locale_Code", order = 1, 
        group = "LocaleImpl_Details", 
        prominent = true, gridOrder = 2)
    protected String localeCode;

    @Column (name = "FRIENDLY_NAME")
    @AdminPresentation(friendlyName = "LocaleImpl_Name", order = 2, 
        group = "LocaleImpl_Details", 
        prominent = true, gridOrder = 1)
    protected String friendlyName;

    @Column (name = "DEFAULT_FLAG")
    @AdminPresentation(friendlyName = "LocaleImpl_Is_Default", order = 3, 
        group = "LocaleImpl_Details", 
        prominent = true, gridOrder = 3)
    protected Boolean defaultFlag = false;

    @ManyToOne(targetEntity = SparkCurrencyImpl.class)
    @JoinColumn(name = "CURRENCY_CODE")
    @AdminPresentation(friendlyName = "LocaleImpl_Currency", order = 4, 
        group = "LocaleImpl_Details", 
        prominent = true)
    protected SparkCurrency defaultCurrency;

    @Column (name = "USE_IN_SEARCH_INDEX")
    @AdminPresentation(friendlyName = "LocaleImpl_Use_In_Search_Index", order = 5, 
        group = "LocaleImpl_Details", 
        prominent = true, gridOrder = 3)
    protected Boolean useInSearchIndex = false;
    
    @Override
    public String getLocaleCode() {
        return localeCode;
    }

    @Override
    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
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
    public void setDefaultFlag(Boolean defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    @Override
    public Boolean getDefaultFlag() {
        if (defaultFlag == null) {
            return Boolean.FALSE;
        } else {
            return defaultFlag;
        }
    }

    @Override
    public SparkCurrency getDefaultCurrency() {
        return defaultCurrency;
    }

    @Override
    public void setDefaultCurrency(SparkCurrency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }
    
    @Override
    public Boolean getUseCountryInSearchIndex() {
        return useInSearchIndex == null ? false : useInSearchIndex;
    }

    @Override
    public void setUseCountryInSearchIndex(Boolean useInSearchIndex) {
        this.useInSearchIndex = useInSearchIndex;
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

        LocaleImpl locale = (LocaleImpl) o;

        if (localeCode != null ? !localeCode.equals(locale.localeCode) : locale.localeCode != null) {
            return false;
        }
        if (friendlyName != null ? !friendlyName.equals(locale.friendlyName) : locale.friendlyName != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = localeCode != null ? localeCode.hashCode() : 0;
        result = 31 * result + (friendlyName != null ? friendlyName.hashCode() : 0);
        return result;
    }
}
