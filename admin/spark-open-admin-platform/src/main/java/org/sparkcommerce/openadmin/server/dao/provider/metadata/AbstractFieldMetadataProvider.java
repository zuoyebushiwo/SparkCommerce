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
package org.sparkcommerce.openadmin.server.dao.provider.metadata;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import org.apache.commons.lang.StringUtils;
import org.sparkcommerce.common.SparkEnumerationType;
import org.sparkcommerce.common.persistence.EntityConfiguration;
import org.sparkcommerce.common.presentation.AdminPresentationClass;
import org.sparkcommerce.common.presentation.OptionFilterParamType;
import org.sparkcommerce.common.presentation.client.SupportedFieldType;
import org.sparkcommerce.common.presentation.override.AdminPresentationMergeEntry;
import org.sparkcommerce.openadmin.dto.BasicFieldMetadata;
import org.sparkcommerce.openadmin.dto.FieldMetadata;
import org.sparkcommerce.openadmin.dto.override.FieldMetadataOverride;
import org.sparkcommerce.openadmin.server.dao.DynamicEntityDao;
import org.sparkcommerce.openadmin.server.dao.FieldInfo;

/**
 * @author Jeff Fischer
 */
public abstract class AbstractFieldMetadataProvider implements FieldMetadataProvider {

    protected Map<String, Map<String, FieldMetadataOverride>> metadataOverrides;

    @Resource(name = "blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    @Resource(name="blMetadataOverrides")
    public void setMetadataOverrides(Map metadataOverrides) {
        try {
            this.metadataOverrides = metadataOverrides;
        } catch (Throwable e) {
            throw new IllegalArgumentException(
                    "Unable to assign metadataOverrides. You are likely using an obsolete spring application context " +
                    "configuration for this value. Please utilize the xmlns:mo=\"http://schema.sparkcommerce.org/mo\" namespace " +
                    "and http://schema.sparkcommerce.org/mo http://schema.sparkcommerce.org/mo/mo.xsd schemaLocation " +
                    "in the xml schema config for your app context. This will allow you to use the appropriate <mo:override> element to configure your overrides.", e);
        }
    }

    protected void setClassOwnership(Class<?> parentClass, Class<?> targetClass, Map<String, FieldMetadata> attributes, FieldInfo field) {
        FieldMetadata metadata = attributes.get(field.getName());
        if (metadata != null) {
            AdminPresentationClass adminPresentationClass;
            if (parentClass != null) {
                metadata.setOwningClass(parentClass.getName());
                adminPresentationClass = parentClass.getAnnotation(AdminPresentationClass.class);
            } else {
                adminPresentationClass = targetClass.getAnnotation(AdminPresentationClass.class);
            }
            if (adminPresentationClass != null) {
                String friendlyName = adminPresentationClass.friendlyName();
                if (!StringUtils.isEmpty(friendlyName) && StringUtils.isEmpty(metadata.getOwningClassFriendlyName())) {
                    metadata.setOwningClassFriendlyName(friendlyName);
                }
            }
        }
    }

    protected FieldInfo buildFieldInfo(Field field) {
        FieldInfo info = new FieldInfo();
        info.setName(field.getName());
        info.setGenericType(field.getGenericType());
        ManyToMany manyToMany = field.getAnnotation(ManyToMany.class);
        if (manyToMany != null) {
            info.setManyToManyMappedBy(manyToMany.mappedBy());
            info.setManyToManyTargetEntity(manyToMany.targetEntity().getName());
        }
        OneToMany oneToMany = field.getAnnotation(OneToMany.class);
        if (oneToMany != null) {
            info.setOneToManyMappedBy(oneToMany.mappedBy());
            info.setOneToManyTargetEntity(oneToMany.targetEntity().getName());
        }
        MapKey mapKey = field.getAnnotation(MapKey.class);
        if (mapKey != null) {
            info.setMapKey(mapKey.name());
        }
        return info;
    }

    /**
     * @deprecated use the overloaded method that takes DynamicEntityDao as well. This version does not always properly detect the override from xml.
     * @param configurationKey
     * @param ceilingEntityFullyQualifiedClassname
     * @return override value
     */
    @Deprecated
    protected Map<String, FieldMetadataOverride> getTargetedOverride(String configurationKey, String ceilingEntityFullyQualifiedClassname) {
        if (metadataOverrides != null && (configurationKey != null || ceilingEntityFullyQualifiedClassname != null)) {
            return metadataOverrides.containsKey(configurationKey)?metadataOverrides.get(configurationKey):metadataOverrides.get(ceilingEntityFullyQualifiedClassname);
        }
        return null;
    }

    protected Map<String, FieldMetadataOverride> getTargetedOverride(DynamicEntityDao dynamicEntityDao, String configurationKey, String ceilingEntityFullyQualifiedClassname) {
        if (metadataOverrides != null && (configurationKey != null || ceilingEntityFullyQualifiedClassname != null)) {
            if (metadataOverrides.containsKey(configurationKey)) {
                return metadataOverrides.get(configurationKey);
            }
            if (metadataOverrides.containsKey(ceilingEntityFullyQualifiedClassname)) {
                return metadataOverrides.get(ceilingEntityFullyQualifiedClassname);
            }
            Class<?> test;
            try {
                test = Class.forName(ceilingEntityFullyQualifiedClassname);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            if (test.isInterface()) {
                //if it's an interface, get the least derive polymorphic concrete implementation
                Class<?>[] types = dynamicEntityDao.getAllPolymorphicEntitiesFromCeiling(test);
                return metadataOverrides.get(types[types.length-1].getName());
            } else {
                //if it's a concrete implementation, try the interfaces
                Class<?>[] types = test.getInterfaces();
                for (Class<?> type : types) {
                    if (metadataOverrides.containsKey(type.getName())) {
                        return metadataOverrides.get(type.getName());
                    }
                }
            }
        }
        return null;
    }

    protected Class<?> getBasicJavaType(SupportedFieldType fieldType) {
        Class<?> response;
        switch (fieldType) {
            case BOOLEAN:
                response = Boolean.TYPE;
                break;
            case DATE:
                response = Date.class;
                break;
            case DECIMAL:
                response = BigDecimal.class;
                break;
            case MONEY:
                response = BigDecimal.class;
                break;
            case INTEGER:
                response = Integer.TYPE;
                break;
            case UNKNOWN:
                response = null;
                break;
            default:
                response = String.class;
                break;
        }

        return response;
    }

    protected Object convertType(String value, OptionFilterParamType type) {
        Object response;
        switch (type) {
            case BIGDECIMAL:
               response = new BigDecimal(value);
               break;
            case BOOLEAN:
               response = Boolean.parseBoolean(value);
               break;
            case DOUBLE:
               response = Double.parseDouble(value);
               break;
            case FLOAT:
               response = Float.parseFloat(value);
               break;
            case INTEGER:
               response = Integer.parseInt(value);
               break;
            case LONG:
               response = Long.parseLong(value);
               break;
            default:
               response = value;
               break;
        }

        return response;
    }

    protected void setupSparkEnumeration(String sparkEnumerationClass, BasicFieldMetadata fieldMetadata, DynamicEntityDao dynamicEntityDao) {
        try {
            
            Map<String, String> enumVals;
            Class<?> sparkEnumeration = Class.forName(sparkEnumerationClass);  

            Method typeMethod = sparkEnumeration.getMethod("getType");
            Method friendlyTypeMethod = sparkEnumeration.getMethod("getFriendlyType");
            Field types = dynamicEntityDao.getFieldManager().getField(sparkEnumeration, "TYPES");
            
            if (Comparable.class.isAssignableFrom(sparkEnumeration)) {
                enumVals = new LinkedHashMap<String, String>();
                Set<SparkEnumerationType> blcEnumSet = new TreeSet<SparkEnumerationType>();
                if (types != null) {
                    Map typesMap = (Map) types.get(null);
                    for (Object value : typesMap.values()) {
                        blcEnumSet.add((SparkEnumerationType) value);
                    }

                    for (Object value : typesMap.values()) {
                        enumVals.put((String) friendlyTypeMethod.invoke(value), (String) typeMethod.invoke(value));
                    }
                }
            } else {
                enumVals = new TreeMap<String, String>();
                if (types != null) {
                    Map typesMap = (Map) types.get(null);
                    for (Object value : typesMap.values()) {
                        enumVals.put((String) friendlyTypeMethod.invoke(value), (String) typeMethod.invoke(value));
                    }
                } else {
                    Field[] fields = dynamicEntityDao.getAllFields(sparkEnumeration);
                    for (Field field : fields) {
                        boolean isStatic = Modifier.isStatic(field.getModifiers());
                        if (isStatic && field.getType().isAssignableFrom(sparkEnumeration)){
                            enumVals.put((String) friendlyTypeMethod.invoke(field.get(null)), (String) typeMethod.invoke(field.get(null)));
                        }
                    }
                }
            }
            
            
            String[][] enumerationValues = new String[enumVals.size()][2];
            int j = 0;
            for (String key : enumVals.keySet()) {
                enumerationValues[j][0] = enumVals.get(key);
                enumerationValues[j][1] = key;
                j++;
            }
            fieldMetadata.setEnumerationValues(enumerationValues);
            fieldMetadata.setEnumerationClass(sparkEnumerationClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Map<String, AdminPresentationMergeEntry> getAdminPresentationEntries(AdminPresentationMergeEntry[] entries) {
        Map<String, AdminPresentationMergeEntry> response = new HashMap<String, AdminPresentationMergeEntry>();
        for (AdminPresentationMergeEntry entry : entries) {
            response.put(entry.propertyType(), entry);
        }
        return response;
    }
}
