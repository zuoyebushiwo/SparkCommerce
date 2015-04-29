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

import org.sparkcommerce.common.exception.ServiceException;
import org.sparkcommerce.openadmin.dto.CriteriaTransferObject;
import org.sparkcommerce.openadmin.dto.DynamicResultSet;
import org.sparkcommerce.openadmin.dto.Entity;
import org.sparkcommerce.openadmin.dto.PersistencePackage;

/**
 * Convenience adapter for PersistenceManagerEventHandler implementations that don't need to implement every
 * method of the interface.
 *
 * @author Jeff Fischer
 */
public class PersistenceManagerEventHandlerAdapter implements PersistenceManagerEventHandler {

    @Override
    public PersistenceManagerEventHandlerResponse postAdd(PersistenceManager persistenceManager, Entity entity, PersistencePackage persistencePackage) throws ServiceException {
        return new PersistenceManagerEventHandlerResponse().withEntity(entity).
                withStatus(PersistenceManagerEventHandlerResponse.PersistenceManagerEventHandlerResponseStatus.NOT_HANDLED);
    }

    @Override
    public PersistenceManagerEventHandlerResponse preFetch(PersistenceManager persistenceManager, PersistencePackage persistencePackage, CriteriaTransferObject cto) throws ServiceException {
        return new PersistenceManagerEventHandlerResponse().
                withStatus(PersistenceManagerEventHandlerResponse.PersistenceManagerEventHandlerResponseStatus.NOT_HANDLED);
    }

    @Override
    public PersistenceManagerEventHandlerResponse postFetch(PersistenceManager persistenceManager, DynamicResultSet resultSet, PersistencePackage persistencePackage,
                                      CriteriaTransferObject cto) throws ServiceException {
        return new PersistenceManagerEventHandlerResponse().withDynamicResultSet(resultSet).
                withStatus(PersistenceManagerEventHandlerResponse.PersistenceManagerEventHandlerResponseStatus.NOT_HANDLED);
    }

    @Override
    public PersistenceManagerEventHandlerResponse preAdd(PersistenceManager persistenceManager, PersistencePackage persistencePackage) throws ServiceException {
        return new PersistenceManagerEventHandlerResponse().
                withStatus(PersistenceManagerEventHandlerResponse.PersistenceManagerEventHandlerResponseStatus.NOT_HANDLED);
    }

    @Override
    public PersistenceManagerEventHandlerResponse preUpdate(PersistenceManager persistenceManager, PersistencePackage persistencePackage) throws ServiceException {
        return new PersistenceManagerEventHandlerResponse().
                withStatus(PersistenceManagerEventHandlerResponse.PersistenceManagerEventHandlerResponseStatus.NOT_HANDLED);
    }

    @Override
    public PersistenceManagerEventHandlerResponse postUpdate(PersistenceManager persistenceManager, Entity entity, PersistencePackage persistencePackage) throws ServiceException {
        return new PersistenceManagerEventHandlerResponse().withEntity(entity).
                withStatus(PersistenceManagerEventHandlerResponse.PersistenceManagerEventHandlerResponseStatus.NOT_HANDLED);
    }

    @Override
    public PersistenceManagerEventHandlerResponse preRemove(PersistenceManager persistenceManager, PersistencePackage persistencePackage) throws ServiceException {
        return new PersistenceManagerEventHandlerResponse().
                withStatus(PersistenceManagerEventHandlerResponse.PersistenceManagerEventHandlerResponseStatus.NOT_HANDLED);
    }

    @Override
    public PersistenceManagerEventHandlerResponse postRemove(PersistenceManager persistenceManager, PersistencePackage persistencePackage) throws ServiceException {
        return new PersistenceManagerEventHandlerResponse().
                withStatus(PersistenceManagerEventHandlerResponse.PersistenceManagerEventHandlerResponseStatus.NOT_HANDLED);
    }

    @Override
    public PersistenceManagerEventHandlerResponse postInspect(PersistenceManager persistenceManager, DynamicResultSet resultSet, PersistencePackage
            persistencePackage) throws ServiceException {
        return new PersistenceManagerEventHandlerResponse().withDynamicResultSet(resultSet).
                withStatus(PersistenceManagerEventHandlerResponse.PersistenceManagerEventHandlerResponseStatus.NOT_HANDLED);
    }

    @Override
    public PersistenceManagerEventHandlerResponse preInspect(PersistenceManager persistenceManager, PersistencePackage persistencePackage) throws ServiceException {
        return new PersistenceManagerEventHandlerResponse().
                withStatus(PersistenceManagerEventHandlerResponse.PersistenceManagerEventHandlerResponseStatus.NOT_HANDLED);
    }

    @Override
    public PersistenceManagerEventHandlerResponse processValidationError(PersistenceManager persistenceManager, Entity entity, PersistencePackage persistencePackage) throws ServiceException {
        return new PersistenceManagerEventHandlerResponse().
                        withStatus(PersistenceManagerEventHandlerResponse.PersistenceManagerEventHandlerResponseStatus.NOT_HANDLED);
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

}
