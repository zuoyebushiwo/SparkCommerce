/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.locale.service;

import org.sparkcommerce.common.locale.domain.Locale;

import java.util.List;

/**
 * @author : Anand Dasari.
 */
public interface LocaleService {

    /**
     * @return the locale for the passed in code
     */
    public Locale findLocaleByCode(String localeCode);

    /**
     * @return the default locale
     */
    public Locale findDefaultLocale();

    /**
     * @return a list of all known locales
     */
    public List<Locale> findAllLocales();
    
    /**
     * Persists the given locale
     * 
     * @param locale
     * @return the persisted locale
     */
    public Locale save(Locale locale);
    
}
