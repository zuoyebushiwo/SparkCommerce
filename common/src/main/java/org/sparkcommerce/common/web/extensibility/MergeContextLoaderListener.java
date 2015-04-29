/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web.extensibility;

import org.sparkcommerce.common.classloader.release.ThreadLocalManager;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

/**
 * Bootstrap listener to start up SparkCommerce's rootId {@link MergeWebApplicationContext}.
 * Simply delegates to {@link MergeContextLoader}.
 *
 * <p>This listener should be registered after
 * {@link org.springframework.web.util.Log4jConfigListener}
 * in <code>web.xml</code>, if the latter is used.
 *
 * @author joel Dasari
 */
public class MergeContextLoaderListener extends ContextLoaderListener {

    /**
     * Create the ContextLoader to use. Can be overridden in subclasses.
     * @return the new ContextLoader
     */
    protected MergeContextLoader createContextLoader() {
        return new MergeContextLoader();
    }


    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        ThreadLocalManager.remove();
    }
}
