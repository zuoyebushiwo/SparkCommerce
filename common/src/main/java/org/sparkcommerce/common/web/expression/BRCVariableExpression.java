/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web.expression;

import org.apache.commons.beanutils.PropertyUtils;
import org.sparkcommerce.common.crossapp.service.CrossAppAuthService;
import org.sparkcommerce.common.sandbox.domain.SandBox;
import org.sparkcommerce.common.time.SystemTime;
import org.sparkcommerce.common.web.SparkRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Date;


/**
 * Exposes the {@link SparkRequestContext} to the Thymeleaf expression context
 * 
 * @author Anand Dasari
 */
public class BRCVariableExpression implements SparkVariableExpression {
    
    @Autowired(required = false)
    @Qualifier("blCrossAppAuthService")
    protected CrossAppAuthService crossAppAuthService;

    @Override
    public String getName() {
        return "brc";
    }
    
    public SandBox getSandbox() {
        SparkRequestContext brc = SparkRequestContext.getSparkRequestContext();
        if (brc != null) {
            return brc.getSandBox();
        }
        return null;
    }
    
    public Date getCurrentTime() {
        return SystemTime.asDate(true);
    }
    
    public Object get(String propertyName) {
        SparkRequestContext brc = SparkRequestContext.getSparkRequestContext();
        if (brc != null) {
            try {
                return PropertyUtils.getProperty(brc, propertyName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public boolean isCsrMode() {
        return crossAppAuthService == null ? false : crossAppAuthService.hasCsrPermission();
    }
    
    public boolean isSandboxMode() {
        SparkRequestContext brc = SparkRequestContext.getSparkRequestContext();
        return (brc == null) ? false : (brc.getSandBox() != null);
    }

}
