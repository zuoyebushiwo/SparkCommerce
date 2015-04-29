/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web;

import org.sparkcommerce.common.locale.domain.Locale;
import org.sparkcommerce.common.locale.service.LocaleService;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Specific Spring component to override the default behavior of {@link CookieLocaleResolver} so that the default Spark
 * Locale looked up in the database is used. This should be hooked up in applicationContext-servlet.xml in place of Spring's
 * {@link CookieResolver}.
 * 
 * @author Anand Dasari
 * @see {@link SparkLocaleResolverImpl}
 */
public class SparkCookieLocaleResolver extends CookieLocaleResolver {

    @Resource(name = "blLocaleService")
    private LocaleService localeService;
    
    @Override
    protected java.util.Locale determineDefaultLocale(HttpServletRequest request) {
        java.util.Locale defaultLocale = getDefaultLocale();
        if (defaultLocale == null) {
            Locale defaultSparkLocale = localeService.findDefaultLocale();
            if (defaultSparkLocale == null) {
                return super.determineDefaultLocale(request);
            } else {
                return SparkRequestContext.convertLocaleToJavaLocale(defaultSparkLocale);
            }
        }
        return defaultLocale;
    }
    
}
