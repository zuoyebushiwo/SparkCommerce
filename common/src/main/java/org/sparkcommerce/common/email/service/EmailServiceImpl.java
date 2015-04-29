/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.email.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.sparkcommerce.common.email.dao.EmailReportingDao;
import org.sparkcommerce.common.email.domain.EmailTarget;
import org.sparkcommerce.common.email.service.exception.EmailException;
import org.sparkcommerce.common.email.service.info.EmailInfo;
import org.sparkcommerce.common.email.service.info.NullEmailInfo;
import org.sparkcommerce.common.email.service.info.ServerInfo;
import org.sparkcommerce.common.email.service.message.EmailPropertyType;
import org.sparkcommerce.common.email.service.message.EmailServiceProducer;
import org.sparkcommerce.common.email.service.message.MessageCreator;
import org.springframework.stereotype.Service;

/**
 * @author jfischer
 */
@Service("blEmailService")
public class EmailServiceImpl implements EmailService {

    @Resource(name = "blEmailTrackingManager")
    protected EmailTrackingManager emailTrackingManager;

    @Resource(name = "blServerInfo")
    protected ServerInfo serverInfo;

    protected EmailServiceProducer emailServiceProducer;

    @Resource(name = "blMessageCreator")
    protected MessageCreator messageCreator;

    @Resource(name = "blEmailReportingDao")
    protected EmailReportingDao emailReportingDao;

    public boolean sendTemplateEmail(EmailTarget emailTarget, EmailInfo emailInfo, Map<String, Object> props) {
        if (props == null) {
            props = new HashMap<String, Object>();
        }
        if (emailInfo == null) {
            emailInfo = new EmailInfo();
        }

        props.put(EmailPropertyType.INFO.getType(), emailInfo);
        props.put(EmailPropertyType.USER.getType(), emailTarget);
        Long emailId = emailTrackingManager.createTrackedEmail(emailTarget.getEmailAddress(), emailInfo.getEmailType(), null);
        props.put("emailTrackingId", emailId);

        return sendBasicEmail(emailInfo, emailTarget, props);
    }

    public boolean sendTemplateEmail(String emailAddress, EmailInfo emailInfo, Map<String, Object> props) {
        if (!(emailInfo instanceof NullEmailInfo)) {
            EmailTarget emailTarget = emailReportingDao.createTarget();
            emailTarget.setEmailAddress(emailAddress);
            return sendTemplateEmail(emailTarget, emailInfo, props);
        } else {
            return true;
        }
    }

    public boolean sendBasicEmail(EmailInfo emailInfo, EmailTarget emailTarget, Map<String, Object> props) {
        if (props == null) {
            props = new HashMap<String, Object>();
        }
        if (emailInfo == null) {
            emailInfo = new EmailInfo();
        }

        props.put(EmailPropertyType.INFO.getType(), emailInfo);
        props.put(EmailPropertyType.USER.getType(), emailTarget);

        if (Boolean.parseBoolean(emailInfo.getSendEmailReliableAsync())) {
            if (emailServiceProducer == null) {
                throw new EmailException("The property sendEmailReliableAsync on EmailInfo is true, but the EmailService does not have an instance of JMSEmailServiceProducer set.");
            }
            emailServiceProducer.send(props);
        } else {
            messageCreator.sendMessage(props);
        }

        return true;
    }

    /**
     * @return the emailTrackingManager
     */
    public EmailTrackingManager getEmailTrackingManager() {
        return emailTrackingManager;
    }

    /**
     * @param emailTrackingManager the emailTrackingManager to set
     */
    public void setEmailTrackingManager(EmailTrackingManager emailTrackingManager) {
        this.emailTrackingManager = emailTrackingManager;
    }

    /**
     * @return the serverInfo
     */
    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    /**
     * @param serverInfo the serverInfo to set
     */
    public void setServerInfo(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }

    /**
     * @return the emailServiceProducer
     */
    public EmailServiceProducer getEmailServiceProducer() {
        return emailServiceProducer;
    }

    /**
     * @param emailServiceProducer the emailServiceProducer to set
     */
    public void setEmailServiceProducer(EmailServiceProducer emailServiceProducer) {
        this.emailServiceProducer = emailServiceProducer;
    }

    /**
     * @return the messageCreator
     */
    public MessageCreator getMessageCreator() {
        return messageCreator;
    }

    /**
     * @param messageCreator the messageCreator to set
     */
    public void setMessageCreator(MessageCreator messageCreator) {
        this.messageCreator = messageCreator;
    }

}
