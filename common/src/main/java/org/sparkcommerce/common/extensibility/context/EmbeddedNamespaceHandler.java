/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.extensibility.context;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Provide the proper parser for the mergeImport element of the custom embedded namespace. See
 * {@code EmbeddedBeanDefinitionParser} for more information.
 *
 * @author Anand Dasari
 */
public class EmbeddedNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("mergeImport", new EmbeddedBeanDefinitionParser());
    }

}
