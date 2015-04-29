/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */

package org.sparkcommerce.common.util.dao;

import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.type.Type;

import java.util.List;
import java.util.Map;

/**
 * Provides utility methods for interacting with dynamic entities
 * 
 * @author Anand Dasari
 */
public interface DynamicDaoHelper {

    public Map<String, Object> getIdMetadata(Class<?> entityClass, HibernateEntityManager entityManager);
    
    public List<String> getPropertyNames(Class<?> entityClass, HibernateEntityManager entityManager);
    
    public List<Type> getPropertyTypes(Class<?> entityClass, HibernateEntityManager entityManager);
    
    public SessionFactory getSessionFactory(HibernateEntityManager entityManager);

    public Class<?>[] getAllPolymorphicEntitiesFromCeiling(Class<?> ceilingClass, SessionFactory sessionFactory, boolean includeUnqualifiedPolymorphicEntities, boolean useCache);

    public Class<?>[] sortEntities(Class<?> ceilingClass, List<Class<?>> entities);

    public boolean isExcludeClassFromPolymorphism(Class<?> clazz);

}
