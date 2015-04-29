/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.email.service.message;

import java.util.Map;

public interface EmailServiceProducer {

    public void send(final Map props);
    
}
