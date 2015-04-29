/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Invocation handler for unit testing byte-weaved classes. Use this InvocationHandler and utility method when Spring is unavailable
 * to complete byte-weaving.
 * 
 * @author Joel Dasari
 */
public class InvocationHandlerForUnitTestingByteWeavedClasses implements InvocationHandler {

    /**
     * This utility method will return a Proxy of a chosen type that response to an array of chose Interfaces and uses a
     * InvocationHandlerForUnitTestingByteWeavedClasses that is backed by an array of chosen Objects.
     * 
     * @param proxyType
     * @param interfaces
     * @param objectsForByteWeaving
     * @return
     */
    public static <T> T createProxy(Class<T> proxyType, Class<?>[] interfaces, Object[] objectsForByteWeaving) {
        InvocationHandler handler = new InvocationHandlerForUnitTestingByteWeavedClasses(objectsForByteWeaving);
        return (T) Proxy.newProxyInstance(handler.getClass().getClassLoader(), interfaces, handler);
    }

    protected Object[] objectsForByteWeaving;

    public InvocationHandlerForUnitTestingByteWeavedClasses(Object[] objectsForByteWeaving) {
        this.objectsForByteWeaving = objectsForByteWeaving;
    }

    /**
     * Will invoke a chosen method against an array of Objects that are meant to be byte-weaved together.  Invoke will return when
     * the first object is found that can be successfully used with the chosen method.  If no objects are found to work with
     * the chosen method, null will be returned.
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        
        for (Object object : objectsForByteWeaving) {
            try {
                return method.invoke(object, args);
            } catch (IllegalArgumentException exception) {
                continue;
            }
        }

        return null;
    }
    
    /**
     * Returns an array of Objects that are meant to be byte-weaved.
     * 
     * @return
     */
    public Object[] getObjectsForByteWeaving() {
        return objectsForByteWeaving;
    }

    /**
     * Sets an array of Objects that are meant to be byte-weaved.
     * 
     * @param objects
     */
    public void setObjectsForByteWeaving(Object[] objects) {
        this.objectsForByteWeaving = objects;
    }

}
