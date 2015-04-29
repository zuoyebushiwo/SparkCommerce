/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.security.service;

import org.sparkcommerce.common.exception.ServiceException;
import org.owasp.validator.html.CleanResults;

/**
 * @author Jeff Fischer
 */
public class CleanStringException extends ServiceException {

    public CleanStringException(CleanResults cleanResults) {
        this.cleanResults = cleanResults;
    }

    protected CleanResults cleanResults;

    public CleanResults getCleanResults() {
        return cleanResults;
    }

    public void setCleanResults(CleanResults cleanResults) {
        this.cleanResults = cleanResults;
    }
}
