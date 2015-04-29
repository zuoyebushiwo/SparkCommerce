/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.config.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sparkcommerce.common.cache.AbstractCacheMissAware;
import org.sparkcommerce.common.cache.PersistentRetrieval;
import org.sparkcommerce.common.config.domain.SystemProperty;
import org.sparkcommerce.common.config.domain.SystemPropertyImpl;
import org.sparkcommerce.common.extensibility.jpa.SiteDiscriminator;
import org.sparkcommerce.common.persistence.EntityConfiguration;
import org.sparkcommerce.common.web.SparkRequestContext;
import org.hibernate.ejb.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * This DAO enables access to manage system properties that can be stored in the database.
 * <p/>
 * @author: Anand Dasari
 * Date: 15/4/2015
 */
@Repository("blSystemPropertiesDao")
public class SystemPropertiesDaoImpl extends AbstractCacheMissAware implements SystemPropertiesDao{

    protected static final Log LOG = LogFactory.getLog(SystemPropertiesDaoImpl.class);

    @PersistenceContext(unitName="blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;
    
    @Override
    public SystemProperty readById(Long id) {
        return em.find(SystemPropertyImpl.class, id);
    }

    @Override
    public SystemProperty saveSystemProperty(SystemProperty systemProperty) {
        return em.merge(systemProperty);
    }

    @Override
    public void deleteSystemProperty(SystemProperty systemProperty) {
        em.remove(systemProperty);
    }

    @Override
    public List<SystemProperty> readAllSystemProperties() {
        TypedQuery<SystemProperty> query = em.createNamedQuery("BC_READ_ALL_SYSTEM_PROPERTIES", SystemProperty.class);
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        return query.getResultList();
    }

    @Override
    public SystemProperty readSystemPropertyByName(final String name) {
        return getCachedObject(SystemProperty.class, "blSystemPropertyNullCheckCache", "SYSTEM_PROPERTY_MISSING_CACHE_HIT_RATE", new PersistentRetrieval<SystemProperty>() {
            @Override
            public SystemProperty retrievePersistentObject() {
                TypedQuery<SystemProperty> query = em.createNamedQuery("BC_READ_SYSTEM_PROPERTIES_BY_NAME", SystemProperty.class);
                query.setParameter("propertyName", name);
                query.setHint(QueryHints.HINT_CACHEABLE, true);
                List<SystemProperty> props = query.getResultList();
                if (props != null && ! props.isEmpty()) {
                    return props.get(0);
                }
                return null;
            }
        }, name, getSite());
    }

    @Override
    public void removeFromCache(SystemProperty systemProperty) {
        String site = "";
        if (systemProperty instanceof SiteDiscriminator && ((SiteDiscriminator) systemProperty).getSiteDiscriminator() != null) {
            site = String.valueOf(((SiteDiscriminator) systemProperty).getSiteDiscriminator());
        }
        super.removeItemFromCache("blSystemPropertyNullCheckCache", systemProperty.getName(), site);
    }

    @Override
    public SystemProperty createNewSystemProperty() {
        return (SystemProperty)entityConfiguration.createEntityInstance(SystemProperty.class.getName());
    }

    @Override
    protected Log getLogger() {
        return LOG;
    }

    protected String getSite() {
        String site = "";
        SparkRequestContext brc = SparkRequestContext.getSparkRequestContext();
        if (brc != null) {
            if (brc.getSite() != null) {
                site = String.valueOf(brc.getSite().getId());
            }
        }
        return site;
    }
}
