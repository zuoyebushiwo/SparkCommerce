/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.vendor.service.monitor.handler;

import org.sparkcommerce.common.email.domain.EmailTarget;
import org.sparkcommerce.common.email.service.EmailService;
import org.sparkcommerce.common.email.service.info.EmailInfo;
import org.sparkcommerce.common.vendor.service.monitor.StatusHandler;
import org.sparkcommerce.common.vendor.service.type.ServiceStatusType;

import javax.annotation.Resource;

public class EmailStatusHandler implements StatusHandler {

    @Resource(name="blEmailService")
    protected EmailService emailService;

    protected EmailInfo emailInfo;
    protected EmailTarget emailTarget;

    public void handleStatus(String serviceName, ServiceStatusType status) {
        String message = serviceName + " is reporting a status of " + status.getType();
        EmailInfo copy = emailInfo.clone();
        copy.setMessageBody(message);
        copy.setSubject(message);
        emailService.sendBasicEmail(copy, emailTarget, null);
    }

    public EmailInfo getEmailInfo() {
        return emailInfo;
    }

    public void setEmailInfo(EmailInfo emailInfo) {
        this.emailInfo = emailInfo;
    }

    public EmailTarget getEmailTarget() {
        return emailTarget;
    }

    public void setEmailTarget(EmailTarget emailTarget) {
        this.emailTarget = emailTarget;
    }

}
