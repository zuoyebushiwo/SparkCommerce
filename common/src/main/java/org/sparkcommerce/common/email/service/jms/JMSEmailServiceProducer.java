/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.email.service.jms;

import org.sparkcommerce.common.email.service.message.EmailServiceProducer;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;

public interface JMSEmailServiceProducer extends EmailServiceProducer {

    /**
     * @return the emailServiceTemplate
     */
    public JmsTemplate getEmailServiceTemplate();

    /**
     * @param emailServiceTemplate the emailServiceTemplate to set
     */
    public void setEmailServiceTemplate(JmsTemplate emailServiceTemplate);

    /**
     * @return the emailServiceDestination
     */
    public Destination getEmailServiceDestination();

    /**
     * @param emailServiceDestination the emailServiceDestination to set
     */
    public void setEmailServiceDestination(Destination emailServiceDestination);

}
