/*
 * #%L
 * SparkCommerce Admin Module
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
package org.sparkcommerce.admin.server.service.handler;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sparkcommerce.common.exception.ServiceException;
import org.sparkcommerce.core.catalog.domain.Category;
import org.sparkcommerce.core.catalog.domain.CategoryXref;
import org.sparkcommerce.core.catalog.domain.CategoryXrefImpl;
import org.sparkcommerce.openadmin.dto.Entity;
import org.sparkcommerce.openadmin.dto.FieldMetadata;
import org.sparkcommerce.openadmin.dto.ForeignKey;
import org.sparkcommerce.openadmin.dto.MergedPropertyType;
import org.sparkcommerce.openadmin.dto.PersistencePackage;
import org.sparkcommerce.openadmin.dto.PersistencePerspective;
import org.sparkcommerce.openadmin.server.dao.DynamicEntityDao;
import org.sparkcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter;
import org.sparkcommerce.openadmin.server.service.persistence.module.RecordHelper;
import org.springframework.stereotype.Component;

/**
 * 
 * @author jfischer
 *
 */
@Component("blCategoryCustomPersistenceHandler")
public class CategoryCustomPersistenceHandler extends CustomPersistenceHandlerAdapter {
    
    private static final Log LOG = LogFactory.getLog(CategoryCustomPersistenceHandler.class);

    @Override
    public Boolean canHandleAdd(PersistencePackage persistencePackage) {
        String ceilingEntityFullyQualifiedClassname = persistencePackage.getCeilingEntityFullyQualifiedClassname();
        String[] customCriteria = persistencePackage.getCustomCriteria();
        return !ArrayUtils.isEmpty(customCriteria) && "addNewCategory".equals(customCriteria[0]) && Category.class.getName().equals(ceilingEntityFullyQualifiedClassname);
    }

    @Override
    public Entity add(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException {
        Entity entity  = persistencePackage.getEntity();
        try {
            PersistencePerspective persistencePerspective = persistencePackage.getPersistencePerspective();
            Category adminInstance = (Category) Class.forName(entity.getType()[0]).newInstance();
            Map<String, FieldMetadata> adminProperties = helper.getSimpleMergedProperties(Category.class.getName(), persistencePerspective);
            adminInstance = (Category) helper.createPopulatedInstance(adminInstance, entity, adminProperties, false);

            CategoryXref categoryXref = new CategoryXrefImpl();
            categoryXref.setCategory(adminInstance.getDefaultParentCategory());
            categoryXref.setSubCategory(adminInstance);
            if (adminInstance.getDefaultParentCategory() != null && !adminInstance.getAllParentCategoryXrefs().contains(categoryXref)) {
                adminInstance.getAllParentCategoryXrefs().add(categoryXref);
            }

            adminInstance = (Category) dynamicEntityDao.merge(adminInstance);

            return helper.getRecord(adminProperties, adminInstance, null, null);
        } catch (Exception e) {
            throw new ServiceException("Unable to add entity for " + entity.getType()[0], e);
        }
    }

    protected Map<String, FieldMetadata> getMergedProperties(Class<?> ceilingEntityFullyQualifiedClass, DynamicEntityDao dynamicEntityDao, Boolean populateManyToOneFields, String[] includeManyToOneFields, String[] excludeManyToOneFields, String configurationKey) throws ClassNotFoundException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        Class<?>[] entities = dynamicEntityDao.getAllPolymorphicEntitiesFromCeiling(ceilingEntityFullyQualifiedClass);
        return dynamicEntityDao.getMergedProperties(
            ceilingEntityFullyQualifiedClass.getName(), 
            entities, 
            null, 
            new String[]{}, 
            new ForeignKey[]{},
            MergedPropertyType.PRIMARY,
            populateManyToOneFields,
            includeManyToOneFields, 
            excludeManyToOneFields,
            configurationKey,
            ""
        );
    }
}
