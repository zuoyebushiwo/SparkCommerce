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
package org.sparkcommerce.openadmin.server.service.persistence.module;

import org.sparkcommerce.common.presentation.client.OperationType;
import org.sparkcommerce.openadmin.dto.ClassMetadata;
import org.sparkcommerce.openadmin.dto.FieldMetadata;
import org.sparkcommerce.openadmin.dto.MergedPropertyType;
import org.sparkcommerce.openadmin.dto.PersistencePerspective;

import java.util.Map;

/**
 * 
 * @author jfischer
 *
 */
public interface InspectHelper {

    public ClassMetadata getMergedClassMetadata(Class<?>[] entities, Map<MergedPropertyType, Map<String, FieldMetadata>> mergedProperties);
    
    public Map<String, FieldMetadata> getSimpleMergedProperties(String entityName, PersistencePerspective persistencePerspective);

    public PersistenceModule getCompatibleModule(OperationType operationType);
    
}
