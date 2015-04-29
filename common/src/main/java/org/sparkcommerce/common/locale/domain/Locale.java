/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.locale.domain;

import org.sparkcommerce.common.currency.domain.SparkCurrency;

import java.io.Serializable;

/**
 * @author : adasari
 */
public interface Locale extends Serializable {

    String getLocaleCode();

    void setLocaleCode(String localeCode);

    public String getFriendlyName();

    public void setFriendlyName(String friendlyName);

    public void setDefaultFlag(Boolean defaultFlag);

    public Boolean getDefaultFlag();

    public SparkCurrency getDefaultCurrency();

    public void setDefaultCurrency(SparkCurrency currency);

    /**
     * If true then the country portion of the locale will be used when building the search index.
     * If null or false then only the language will be used.
     * 
     * For example, if false, a locale of en_US will only index the results based
     * on the root of "en".
     * 
     * @return
     */
    public Boolean getUseCountryInSearchIndex();
    
    /**
     * Sets whether or not to use the country portion of the locale in the search index.
     * @param useInSearchIndex
     */
    public void setUseCountryInSearchIndex(Boolean useInSearchIndex);

}
