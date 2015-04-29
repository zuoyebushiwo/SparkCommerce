/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.email.dao;

import org.sparkcommerce.common.email.domain.EmailTarget;
import org.sparkcommerce.common.email.domain.EmailTracking;
import org.sparkcommerce.common.email.domain.EmailTrackingClicks;
import org.sparkcommerce.common.email.domain.EmailTrackingOpens;
import org.sparkcommerce.common.persistence.EntityConfiguration;
import org.sparkcommerce.common.time.SystemTime;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author jfischer
 *
 */
@Repository("blEmailReportingDao")
public class EmailReportingDaoImpl implements EmailReportingDao {

    @PersistenceContext(unitName="blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    /* (non-Javadoc)
     * @see WebReportingDao#createTracking(java.lang.String, java.lang.String, java.lang.String)
     */
    public Long createTracking(String emailAddress, String type, String extraValue) {
        EmailTracking tracking = (EmailTracking) entityConfiguration.createEntityInstance("org.sparkcommerce.common.email.domain.EmailTracking");
        tracking.setDateSent(SystemTime.asDate());
        tracking.setEmailAddress(emailAddress);
        tracking.setType(type);

        em.persist(tracking);

        return tracking.getId();
    }

    public EmailTarget createTarget() {
        EmailTarget target = (EmailTarget) entityConfiguration.createEntityInstance("org.sparkcommerce.common.email.domain.EmailTarget");
        return target;
    }

    @SuppressWarnings("unchecked")
    public EmailTracking retrieveTracking(Long emailId) {
        return (EmailTracking) em.find(entityConfiguration.lookupEntityClass("org.sparkcommerce.common.email.domain.EmailTracking"), emailId);
    }

    public void recordOpen(Long emailId, String userAgent) {
        EmailTrackingOpens opens = (EmailTrackingOpens) entityConfiguration.createEntityInstance("org.sparkcommerce.common.email.domain.EmailTrackingOpens");
        opens.setEmailTracking(retrieveTracking(emailId));
        opens.setDateOpened(SystemTime.asDate());
        opens.setUserAgent(userAgent);

        em.persist(opens);
    }

    public void recordClick(Long emailId, String customerId, String destinationUri, String queryString) {
        EmailTrackingClicks clicks = (EmailTrackingClicks) entityConfiguration.createEntityInstance("org.sparkcommerce.common.email.domain.EmailTrackingClicks");
        clicks.setEmailTracking(retrieveTracking(emailId));
        clicks.setDateClicked(SystemTime.asDate());
        clicks.setDestinationUri(destinationUri);
        clicks.setQueryString(queryString);
        clicks.setCustomerId(customerId);

        em.persist(clicks);
    }
}
