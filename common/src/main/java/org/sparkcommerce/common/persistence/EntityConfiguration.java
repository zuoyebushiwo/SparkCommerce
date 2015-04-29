/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

@Component("blEntityConfiguration")
public class EntityConfiguration implements ApplicationContextAware {

    private static final Log LOG = LogFactory.getLog(EntityConfiguration.class);

    private ApplicationContext webApplicationContext;
    private final HashMap<String, Class<?>> entityMap = new HashMap<String, Class<?>>(50);
    private ApplicationContext applicationcontext;
    private Resource[] entityContexts;

    @javax.annotation.Resource(name="blMergedEntityContexts")
    protected Set<String> mergedEntityContexts;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.webApplicationContext = applicationContext;
    }

    @PostConstruct
    public void configureMergedItems() {
        Set<Resource> temp = new LinkedHashSet<Resource>();
        if (mergedEntityContexts != null && !mergedEntityContexts.isEmpty()) {
            for (String location : mergedEntityContexts) {
                temp.add(webApplicationContext.getResource(location));
            }
        }
        if (entityContexts != null) {
            for (Resource resource : entityContexts) {
                temp.add(resource);
            }
        }
        entityContexts = temp.toArray(new Resource[temp.size()]);
        applicationcontext = new GenericXmlApplicationContext(entityContexts);
    }

    public Class<?> lookupEntityClass(String beanId) {
        Class<?> clazz;
        if (entityMap.containsKey(beanId)) {
            clazz = entityMap.get(beanId);
        } else {
            Object object = applicationcontext.getBean(beanId);
            clazz = object.getClass();
            entityMap.put(beanId, clazz);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Returning class (" + clazz.getName() + ") configured with bean id (" + beanId + ')');
        }
        return clazz;
    }
    
    public String[] getEntityBeanNames() {
        return applicationcontext.getBeanDefinitionNames();
    }

    public <T> Class<T> lookupEntityClass(String beanId, Class<T> resultClass) {
        Class<T> clazz;
        if (entityMap.containsKey(beanId)) {
            clazz = (Class<T>) entityMap.get(beanId);
        } else {
            Object object = applicationcontext.getBean(beanId);
            clazz = (Class<T>) object.getClass();
            entityMap.put(beanId, clazz);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Returning class (" + clazz.getName() + ") configured with bean id (" + beanId + ')');
        }
        return clazz;
    }

    public Object createEntityInstance(String beanId) {
        Object bean = applicationcontext.getBean(beanId);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Returning instance of class (" + bean.getClass().getName() + ") configured with bean id (" + beanId + ')');
        }
        return bean;
    }

    public <T> T createEntityInstance(String beanId, Class<T> resultClass) {
        T bean = (T) applicationcontext.getBean(beanId);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Returning instance of class (" + bean.getClass().getName() + ") configured with bean id (" + beanId + ')');
        }
        return bean;
    }

    public Resource[] getEntityContexts() {
        return entityContexts;
    }

    public void setEntityContexts(Resource[] entityContexts) {
        this.entityContexts = entityContexts;
    }
}
