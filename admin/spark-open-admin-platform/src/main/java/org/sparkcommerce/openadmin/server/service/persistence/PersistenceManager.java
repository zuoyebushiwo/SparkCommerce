/*
 * #%L
 * SparkCommerce Open Admin Platform
 * %%
 * Copyright (C) 2009 - 2013 Spark Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.sparkcommerce.openadmin.server.service.persistence;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.sparkcommerce.common.exception.ServiceException;
import org.sparkcommerce.openadmin.dto.ClassMetadata;
import org.sparkcommerce.openadmin.dto.CriteriaTransferObject;
import org.sparkcommerce.openadmin.dto.FieldMetadata;
import org.sparkcommerce.openadmin.dto.MergedPropertyType;
import org.sparkcommerce.openadmin.dto.PersistencePackage;
import org.sparkcommerce.openadmin.dto.PersistencePerspective;
import org.sparkcommerce.openadmin.server.dao.DynamicEntityDao;
import org.sparkcommerce.openadmin.server.service.handler.CustomPersistenceHandler;

public interface PersistenceManager {

    public abstract Class<?>[] getAllPolymorphicEntitiesFromCeiling(Class<?> ceilingClass);

    public abstract Class<?>[] getPolymorphicEntities(String ceilingEntityFullyQualifiedClassname) throws ClassNotFoundException;

    public abstract Map<String, FieldMetadata> getSimpleMergedProperties(String entityName, PersistencePerspective persistencePerspective) throws ClassNotFoundException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException;

    public abstract ClassMetadata getMergedClassMetadata(Class<?>[] entities, Map<MergedPropertyType, Map<String, FieldMetadata>> mergedProperties) throws ClassNotFoundException, IllegalArgumentException;

    public abstract PersistenceResponse inspect(PersistencePackage persistencePackage) throws ServiceException, ClassNotFoundException;

    public abstract PersistenceResponse fetch(PersistencePackage persistencePackage, CriteriaTransferObject cto) throws ServiceException;

    public abstract PersistenceResponse add(PersistencePackage persistencePackage) throws ServiceException;

    public abstract PersistenceResponse update(PersistencePackage persistencePackage) throws ServiceException;

    public abstract PersistenceResponse remove(PersistencePackage persistencePackage) throws ServiceException;

    public abstract DynamicEntityDao getDynamicEntityDao();

    public abstract void setDynamicEntityDao(DynamicEntityDao dynamicEntityDao);

    public abstract Map<String, String> getTargetEntityManagers();

    public abstract void setTargetEntityManagers(Map<String, String> targetEntityManagers);

    public abstract TargetModeType getTargetMode();

    public abstract void setTargetMode(TargetModeType targetMode);

    public abstract List<CustomPersistenceHandler> getCustomPersistenceHandlers();

    public abstract void setCustomPersistenceHandlers(List<CustomPersistenceHandler> customPersistenceHandlers);

    public abstract Class<?>[] getUpDownInheritance(Class<?> testClass);

    public abstract Class<?>[] getUpDownInheritance(String testClassname) throws ClassNotFoundException;

}
