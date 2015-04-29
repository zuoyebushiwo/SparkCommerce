/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

/**
 * @author Joel Dasari
 */
public class SparkResourceHttpRequestBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {

        String[] names = factory.getBeanNamesForType(ResourceHttpRequestHandler.class);

        for (String name : names) {
            BeanDefinition bd = factory.getBeanDefinition(name);
            bd.setBeanClassName(SparkGWTModuleURLMappingResourceHttpRequestHandler.class.getName());
        }
    }
}
