/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.email.service;

import org.sparkcommerce.common.email.domain.EmailTarget;
import org.sparkcommerce.common.email.service.info.EmailInfo;

import java.util.Map;

/**
 * This null implementation class can be used during development to work around
 * the need to have a working SMTP service to route emails through.
 * 
 * @author jdasari
 */
public class NullEmailServiceImpl implements EmailService {

    @Override
    public boolean sendTemplateEmail(String emailAddress, EmailInfo emailInfo, Map<String, Object> props) {
        return true;
    }

    @Override
    public boolean sendTemplateEmail(EmailTarget emailTarget, EmailInfo emailInfo, Map<String, Object> props) {
        return true;
    }

    @Override
    public boolean sendBasicEmail(EmailInfo emailInfo, EmailTarget emailTarget, Map<String, Object> props) {
        return true;
    }

}
