/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.email.service;

import java.util.Map;


/**
 * @author jdasari
 *
 */
public interface EmailTrackingManager {

    public Long createTrackedEmail(String emailAddress, String type, String extraValue);
    public void recordOpen (Long emailId, Map<String, String> extraValues);
    public void recordClick(Long emailId , Map<String, String> parameterMap, String customerId, Map<String, String> extraValues);

}
