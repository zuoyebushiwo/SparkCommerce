/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.locale.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sparkcommerce.common.locale.dao.LocaleDao;
import org.sparkcommerce.common.locale.domain.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : Anand Dasari
 */
@Service("blLocaleService")
public class LocaleServiceImpl implements LocaleService {
    private static final Log LOG = LogFactory.getLog(LocaleServiceImpl.class);

    @Resource(name="blLocaleDao")
    protected LocaleDao localeDao;

    @Override
    public Locale findLocaleByCode(String localeCode) {
        return localeDao.findLocaleByCode(localeCode);
    }
    
    @Override
    public Locale findDefaultLocale() {
        return localeDao.findDefaultLocale();
    }

    @Override
    public List<Locale> findAllLocales() {
        return localeDao.findAllLocales();
    }
    
    @Override
    @Transactional("blTransactionManager")
    public Locale save(Locale locale) {
        return localeDao.save(locale);
    }
    
}
