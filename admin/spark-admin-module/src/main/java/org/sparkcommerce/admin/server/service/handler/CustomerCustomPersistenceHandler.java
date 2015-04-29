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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sparkcommerce.common.exception.ServiceException;
import org.sparkcommerce.common.presentation.client.OperationType;
import org.sparkcommerce.openadmin.dto.Entity;
import org.sparkcommerce.openadmin.dto.FieldMetadata;
import org.sparkcommerce.openadmin.dto.PersistencePackage;
import org.sparkcommerce.openadmin.dto.PersistencePerspective;
import org.sparkcommerce.openadmin.server.dao.DynamicEntityDao;
import org.sparkcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter;
import org.sparkcommerce.openadmin.server.service.persistence.module.RecordHelper;
import org.sparkcommerce.profile.core.dao.RoleDao;
import org.sparkcommerce.profile.core.domain.Customer;
import org.sparkcommerce.profile.core.domain.CustomerRole;
import org.sparkcommerce.profile.core.service.CustomerService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * @author jfischer
 */
@Component("blCustomerCustomPersistenceHandler")
public class CustomerCustomPersistenceHandler extends CustomPersistenceHandlerAdapter {

    private static final Log LOG = LogFactory.getLog(CustomerCustomPersistenceHandler.class);

    @Resource(name="blCustomerService")
    protected CustomerService customerService;
    
    @Resource(name="blRoleDao")
    protected RoleDao roleDao;

    @Override
    public Boolean canHandleAdd(PersistencePackage persistencePackage) {
        return persistencePackage.getCeilingEntityFullyQualifiedClassname() != null && persistencePackage.getCeilingEntityFullyQualifiedClassname().equals(Customer.class.getName());
    }

    @Override
    public Boolean canHandleRemove(PersistencePackage persistencePackage) {
        return persistencePackage.getCeilingEntityFullyQualifiedClassname() != null && persistencePackage.getCeilingEntityFullyQualifiedClassname().equals(Customer.class.getName());
    }
    
    @Override
    public Entity add(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException {
        Entity entity  = persistencePackage.getEntity();
        try {
            PersistencePerspective persistencePerspective = persistencePackage.getPersistencePerspective();
            Customer adminInstance = (Customer) Class.forName(entity.getType()[0]).newInstance();
            adminInstance.setId(customerService.findNextCustomerId());
            Map<String, FieldMetadata> adminProperties = helper.getSimpleMergedProperties(Customer.class.getName(), persistencePerspective);
            adminInstance = (Customer) helper.createPopulatedInstance(adminInstance, entity, adminProperties, false);
            
            if (customerService.readCustomerByUsername(adminInstance.getUsername()) != null) {
                Entity error = new Entity();
                error.addValidationError("username", "nonUniqueUsernameError");
                return error;
            }
            
            adminInstance = (Customer) dynamicEntityDao.merge(adminInstance);
            Entity adminEntity = helper.getRecord(adminProperties, adminInstance, null, null);

            return adminEntity;
        } catch (Exception e) {
            LOG.error("Unable to execute persistence activity", e);
            throw new ServiceException("Unable to add entity for " + entity.getType()[0], e);
        }
    }
    
    @Override
    public void remove(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException {
    	Entity entity = persistencePackage.getEntity();
    	try {
			roleDao.removeCustomerRolesByCustomerId(Long.parseLong(entity.findProperty("id").getValue()));
			helper.getCompatibleModule(OperationType.BASIC).remove(persistencePackage);
		} catch (Exception e) {
			LOG.error("Unable to execute persistence activity", e);
            throw new ServiceException("Unable to remove entity for " + entity.getType()[0], e);
		}
    }
}
