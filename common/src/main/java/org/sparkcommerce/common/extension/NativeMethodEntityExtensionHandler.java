/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.extension;

import org.sparkcommerce.common.site.domain.SiteImpl;
import org.sparkcommerce.common.web.SparkRequestContext;

/**
 * <p>
 * Extension handler used for contributions to native Object methods on entity like equals() clone() and hashCode(). It is
 * possible that dynamically weaved in columns from embeddables need to contribute to those methods.
 * 
 * <p>
 * Managers that implement this handler should add themselves to the {@code blEntityExtensionManagers} list:
 * 
 * <pre>
 * {@code
 * <bean id="myEntityExtensionManagers" class="org.springframework.beans.factory.config.MapFactoryBean">
 *       <property name="sourceMap">
 *           <map>
 *               <entry key="blSomeEntityExtensionManager" value-ref="blSomeEntityExtensionManager"/>
 *           </map>
 *       </property>
 *   </bean>
 *
 *   <bean class="org.sparkcommerce.common.extensibility.context.merge.LateStageMergeBeanPostProcessor">
 *       <property name="collectionRef" value="myEntityExtensionManagers"/>
 *       <property name="targetRef" value="blEntityExtensionManagers"/>
 *   </bean>
 * }
 * </pre>
 * 
 * <p>
 * These {@link blEntityExtensionManagers} are added onto the {@link SparkRequestContext} with
 * {@link SparkRequestContext#getAdditionalProperties()}. Native methods can use that to pull it off
 * 
 * <p>
 * Example use case is in {@link SiteImpl#clone} as well as {@link CategoryImpl#getAllChildCategoryXrefs()}
 * 
 * @author Phillip Verheyden (phillipuniverse)
 */
public interface NativeMethodEntityExtensionHandler<T> extends ExtensionHandler {

    /**
     * Contributes additional properties to a cloned instance. Implementors should take properties from <b>from</b> and
     * copy them over to <b>to</b>
     * 
     * @param from the original instance that is being cloned
     * @param to the instance that <b>from</b> is being cloned to
     */
    public void contributeClone(T from, T to);

    /**
     * Contributes additional equals() checks. Dynamically weaved properties should check between <b>original</b> and 
     * <b>test</b> and store the result in <b>result</b>.
     * 
     * @param original the instance being checked for equals (the 'this' side of equals)
     * @param test the instance being compared to <b>original</b> for equality
     * @param result where the result should be stored if this extension manager
     */
    public void contributeEquals(T original, T test, ExtensionResultHolder<Boolean> result);
    
    /**
     * Contributes more properties to build an object's hashCode().
     * 
     * @param entity the entity whose hashCode is being computed
     * @param precomputedHashCode the hashCode precomputed from the existing properties on <b>entity</b>
     * @param result where the final hashCode() should be stored
     */
    public void contributeHashCode(T entity, int precomputedHashCode, ExtensionResultHolder<Integer> result);

}
