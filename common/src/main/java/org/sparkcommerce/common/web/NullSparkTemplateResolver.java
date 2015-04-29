/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web;

import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolution;

/**
 * Placeholder component to support a custom TemplateResolver.
 * 
 * Utilized by the Spark Commerce CustomTemplate extension to introduce themes at the DB level.
 *
 * @author jdasari
 */
public class NullSparkTemplateResolver implements ITemplateResolver {

    @Override
    public String getName() {
        return "NullSparkTemplateResolver";
    }

    @Override
    public Integer getOrder() {
        return 9999;
    }

    @Override
    public TemplateResolution resolveTemplate(TemplateProcessingParameters templateProcessingParameters) {
        return null;
    }

    @Override
    public void initialize() {

    }
}
