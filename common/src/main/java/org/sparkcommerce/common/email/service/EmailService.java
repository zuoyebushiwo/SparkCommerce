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
 * @author jfischer
 *
 */
public interface EmailService {

    public boolean sendTemplateEmail(String emailAddress, EmailInfo emailInfo,  Map<String,Object> props);

    public boolean sendTemplateEmail(EmailTarget emailTarget, EmailInfo emailInfo, Map<String,Object> props);

    public boolean sendBasicEmail(EmailInfo emailInfo, EmailTarget emailTarget, Map<String,Object> props);

}
