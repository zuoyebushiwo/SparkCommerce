/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.audit;

import org.sparkcommerce.common.time.SystemTime;
import org.sparkcommerce.common.web.SparkRequestContext;
import org.sparkcommerce.common.web.SparkRequestCustomerResolverImpl;

import java.lang.reflect.Field;
import java.util.Calendar;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class AuditableListener {

    @PrePersist
    public void setAuditCreatedBy(Object entity) throws Exception {
        if (entity.getClass().isAnnotationPresent(Entity.class)) {
            Field field = getSingleField(entity.getClass(), "auditable");
            field.setAccessible(true);
            if (field.isAnnotationPresent(Embedded.class)) {
                Object auditable = field.get(entity);
                if (auditable == null) {
                    field.set(entity, new Auditable());
                    auditable = field.get(entity);
                }
                Field temporalField = auditable.getClass().getDeclaredField("dateCreated");
                Field agentField = auditable.getClass().getDeclaredField("createdBy");
                setAuditValueTemporal(temporalField, auditable);
                setAuditValueAgent(agentField, auditable);
            }
        }
    }
    
    @PreUpdate
    public void setAuditUpdatedBy(Object entity) throws Exception {
        if (entity.getClass().isAnnotationPresent(Entity.class)) {
            Field field = getSingleField(entity.getClass(), "auditable");
            field.setAccessible(true);
            if (field.isAnnotationPresent(Embedded.class)) {
                Object auditable = field.get(entity);
                if (auditable == null) {
                    field.set(entity, new Auditable());
                    auditable = field.get(entity);
                }
                Field temporalField = auditable.getClass().getDeclaredField("dateUpdated");
                Field agentField = auditable.getClass().getDeclaredField("updatedBy");
                setAuditValueTemporal(temporalField, auditable);
                setAuditValueAgent(agentField, auditable);
            }
        }
    }
    
    protected void setAuditValueTemporal(Field field, Object entity) throws IllegalArgumentException, IllegalAccessException {
        Calendar cal = SystemTime.asCalendar();
        field.setAccessible(true);
        field.set(entity, cal.getTime());
    }
    
    protected void setAuditValueAgent(Field field, Object entity) throws IllegalArgumentException, IllegalAccessException {
        Long customerId = 0L;
        try {
            SparkRequestContext requestContext = SparkRequestContext.getSparkRequestContext();
            if (requestContext != null && requestContext.getWebRequest() != null) {
                Object customer = SparkRequestCustomerResolverImpl.getRequestCustomerResolver().getCustomer();
                if (customer != null) {
                    Class<?> customerClass = customer.getClass();
                    Field userNameField = getSingleField(customerClass, "username");
                    userNameField.setAccessible(true);
                    String username = (String) userNameField.get(customer);
                    if (username != null) {
                        //the customer has been persisted
                        Field idField = getSingleField(customerClass, "id");
                        idField.setAccessible(true);
                        customerId = (Long) idField.get(customer);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        field.setAccessible(true);
        field.set(entity, customerId);
    }

    private Field getSingleField(Class<?> clazz, String fieldName) throws IllegalStateException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException nsf) {
            // Try superclass
            if (clazz.getSuperclass() != null) {
                return getSingleField(clazz.getSuperclass(), fieldName);
            }

            return null;
        }
    }
    
}
