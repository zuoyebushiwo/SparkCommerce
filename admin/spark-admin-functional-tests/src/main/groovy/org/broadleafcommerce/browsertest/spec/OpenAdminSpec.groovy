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
package org.sparkcommerce.browsertest.spec

import org.sparkcommerce.browsertest.page.AdminLoginPage
import org.sparkcommerce.browsertest.page.AdminPage

import geb.spock.GebSpec


class OpenAdminSpec extends GebSpec {

    def "can succesfully login to the admin"() {
        given:
        to AdminLoginPage

        when:
        username = "admin"
        password = "admin"

        and:
        submitButton.click()

        then:
        at AdminPage
    }
}