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

/**
 * @author jfischer
 *
 */
public interface EmailReportingDao {

    public Long createTracking(String emailAddress, String type, String extraValue) ;
    public void recordOpen(Long emailId, String userAgent);
    public void recordClick(Long emailId, String customerId, String destinationUri, String queryString);
    public EmailTracking retrieveTracking(Long emailId);
    public EmailTarget createTarget();

}
