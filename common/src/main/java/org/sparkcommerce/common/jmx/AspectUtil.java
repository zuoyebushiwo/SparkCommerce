/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.jmx;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;

public class AspectUtil {

    public static Object exposeRootBean(Object managedBean) {
        try {
            if (AopUtils.isAopProxy(managedBean) && managedBean instanceof Advised) {
                Advised advised = (Advised) managedBean;
                managedBean = advised.getTargetSource().getTarget();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return managedBean;
    }

}
