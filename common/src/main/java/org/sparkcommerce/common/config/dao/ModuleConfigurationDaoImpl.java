/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.config.dao;

import org.sparkcommerce.common.config.domain.AbstractModuleConfiguration;
import org.sparkcommerce.common.config.domain.ModuleConfiguration;
import org.sparkcommerce.common.config.service.type.ModuleConfigurationType;
import org.sparkcommerce.common.persistence.EntityConfiguration;
import org.sparkcommerce.common.persistence.Status;
import org.sparkcommerce.common.time.SystemTime;
import org.hibernate.ejb.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository("blModuleConfigurationDao")
public class ModuleConfigurationDaoImpl implements ModuleConfigurationDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name = "blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    protected Long currentDateResolution = 10000L;
    protected Date cachedDate = SystemTime.asDate();

    protected Date getCurrentDateAfterFactoringInDateResolution() {
        Date returnDate = SystemTime.getCurrentDateWithinTimeResolution(cachedDate, currentDateResolution);
        if (returnDate != cachedDate) {
            if (SystemTime.shouldCacheDate()) {
                cachedDate = returnDate;
            }
        }
        return returnDate;
    }

    @Override
    public ModuleConfiguration readById(Long id) {
        return em.find(AbstractModuleConfiguration.class, id);
    }

    @Override
    public ModuleConfiguration save(ModuleConfiguration config) {
        if (config.getIsDefault()) {
            Query batchUpdate = em.createNamedQuery("BC_BATCH_UPDATE_MODULE_CONFIG_DEFAULT");
            batchUpdate.setParameter("configType", config.getModuleConfigurationType().getType());
            batchUpdate.executeUpdate();
        }
        return em.merge(config);
    }

    @Override
    public void delete(ModuleConfiguration config) {
        ((Status) config).setArchived('Y');
        em.merge(config);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ModuleConfiguration> readAllByType(ModuleConfigurationType type) {
        Query query = em.createNamedQuery("BC_READ_MODULE_CONFIG_BY_TYPE");
        query.setParameter("configType", type.getType());
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        query.setHint(QueryHints.HINT_CACHE_REGION, "blConfigurationModuleElements");
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ModuleConfiguration> readActiveByType(ModuleConfigurationType type) {
        Query query = em.createNamedQuery("BC_READ_ACTIVE_MODULE_CONFIG_BY_TYPE");
        query.setParameter("configType", type.getType());

        Date myDate = getCurrentDateAfterFactoringInDateResolution();

        query.setParameter("currentDate", myDate);
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        query.setHint(QueryHints.HINT_CACHE_REGION, "blConfigurationModuleElements");
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ModuleConfiguration> readByType(Class<? extends ModuleConfiguration> type) {
        //TODO change this to a JPA criteria expression
        Query query = em.createQuery("SELECT config FROM " + type.getName() + " config");
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        query.setHint(QueryHints.HINT_CACHE_REGION, "blConfigurationModuleElements");
        return query.getResultList();
    }

    @Override
    public Long getCurrentDateResolution() {
        return currentDateResolution;
    }

    @Override
    public void setCurrentDateResolution(Long currentDateResolution) {
        this.currentDateResolution = currentDateResolution;
    }
}
