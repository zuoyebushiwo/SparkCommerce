/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.extensibility.jpa.copy;

/**
 * @author Anand Dasari
 */
public class DirectCopyIgnorePattern {

    private String[] patterns;
    private String[] templateTokenPatterns;

    public String[] getPatterns() {
        return patterns;
    }

    public void setPatterns(String[] patterns) {
        this.patterns = patterns;
    }

    public String[] getTemplateTokenPatterns() {
        return templateTokenPatterns;
    }

    public void setTemplateTokenPatterns(String[] templateTokenPatterns) {
        this.templateTokenPatterns = templateTokenPatterns;
    }

}
