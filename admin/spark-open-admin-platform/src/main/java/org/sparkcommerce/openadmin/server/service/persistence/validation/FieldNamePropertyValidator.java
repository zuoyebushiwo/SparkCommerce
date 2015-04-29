/*
 * #%L
 * SparkCommerce Open Admin Platform
 * %%
 * Copyright (C) 2009 - 2014 Spark Commerce
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
package org.sparkcommerce.openadmin.server.service.persistence.validation;

/**
 * Marker interface for any validator that contains field names as part of its configuration. This interface is checked
 * during property building and will be used to modify the field name configuration item. Take the following example,
 * AdminUserImpl declares a password match on the field passwordConfirm. However, if the AdminUserImpl instance
 * is associated to another entity, then the password match field should actually become [another entity].[admin user field].passwordConfirm.
 *
 * @author Jeff Fischer
 */
public interface FieldNamePropertyValidator {
}