/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web;

import org.springframework.web.context.request.WebRequest;


/**
 * Provides no-op implementations to optional methods
 */
public abstract class AbstractSparkWebRequestProcessor implements SparkWebRequestProcessor {

    
    public void postProcess(WebRequest request) {
        // nada
    }

}
