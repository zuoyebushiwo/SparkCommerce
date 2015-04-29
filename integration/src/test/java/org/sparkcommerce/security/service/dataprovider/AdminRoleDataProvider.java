/*
 * #%L
 * SparkCommerce Integration
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
package org.sparkcommerce.security.service.dataprovider;

import org.sparkcommerce.openadmin.server.security.domain.AdminRole;
import org.sparkcommerce.openadmin.server.security.domain.AdminRoleImpl;
import org.testng.annotations.DataProvider;

public class AdminRoleDataProvider {
    @DataProvider(name = "setupAdminRole")
    public static Object[][] createAdminRole() {
        AdminRole adminRole = new AdminRoleImpl();
        adminRole.setName("TestAdminUserRole");
        adminRole.setDescription("Test Admin Role");

        return new Object[][] { new Object[] { adminRole } };
    }
}