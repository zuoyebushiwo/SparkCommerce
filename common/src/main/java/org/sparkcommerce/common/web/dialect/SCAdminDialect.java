/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web.dialect;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

public class SCAdminDialect extends AbstractDialect {

    private Set<IProcessor> processors = new HashSet<IProcessor>();

    @Override
    public String getPrefix() {
        return "blc_admin";
    }

    @Override
    public boolean isLenient() {
        return true;
    }

    @Override
    public Set<IProcessor> getProcessors() {
        return processors;
    }

    public void setProcessors(Set<IProcessor> processors) {
        this.processors = processors;
    }

}
