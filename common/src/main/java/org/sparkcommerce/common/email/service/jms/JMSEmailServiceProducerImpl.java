/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.email.service.jms;

import org.sparkcommerce.common.email.service.info.EmailInfo;
import org.sparkcommerce.common.email.service.message.EmailPropertyType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.io.Serializable;
import java.util.Map;

/**
 * @author jfischer
 *
 */
public class JMSEmailServiceProducerImpl implements JMSEmailServiceProducer {

    private JmsTemplate emailServiceTemplate;

    private Destination emailServiceDestination;

    public void send(final Map props) {
        if (props instanceof Serializable) {
            final Serializable sProps = (Serializable) props;
            emailServiceTemplate.send(emailServiceDestination, new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                    ObjectMessage message = session.createObjectMessage(sProps);
                    EmailInfo info = (EmailInfo) props.get(EmailPropertyType.INFO.getType());
                    message.setJMSPriority(Integer.parseInt(info.getSendAsyncPriority()));
                    return message;
                }
            });
        } else {
            throw new IllegalArgumentException("The properties map must be Serializable");
        }
    }

    /**
     * @return the emailServiceTemplate
     */
    public JmsTemplate getEmailServiceTemplate() {
        return emailServiceTemplate;
    }

    /**
     * @param emailServiceTemplate the emailServiceTemplate to set
     */
    public void setEmailServiceTemplate(JmsTemplate emailServiceTemplate) {
        this.emailServiceTemplate = emailServiceTemplate;
    }

    /**
     * @return the emailServiceDestination
     */
    public Destination getEmailServiceDestination() {
        return emailServiceDestination;
    }

    /**
     * @param emailServiceDestination the emailServiceDestination to set
     */
    public void setEmailServiceDestination(Destination emailServiceDestination) {
        this.emailServiceDestination = emailServiceDestination;
    }

}
