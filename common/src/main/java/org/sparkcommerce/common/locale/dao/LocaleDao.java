/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.locale.dao;

import org.sparkcommerce.common.locale.domain.Locale;

import java.util.List;

/**
 * @author: Adasari.
 */
public interface LocaleDao {

    /**
     * @return The locale for the passed in code
     */
    public Locale findLocaleByCode(String localeCode);

    /**
     * Returns the page template with the passed in id.
     *
     * @return The default locale
     */
    public Locale findDefaultLocale();

    /**
     * Returns all supported SC locales.
     * @return
     */
    public List<Locale> findAllLocales();
    
    public Locale save(Locale locale);

}
