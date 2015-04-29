/*
 * #%L
 * SparkCommerce CMS Module
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
package org.sparkcommerce.cms.web;

import org.sparkcommerce.cms.web.SparkProcessURLFilter;

import junit.framework.TestCase;

/**
 * Created by bpolster.
 */
public class SparkProcessURLFilterTest extends TestCase {
    public void testShouldProcessURL() throws Exception {
        SparkProcessURLFilter cf = new SparkProcessURLFilter();
        // Should fail
        assertFalse("Image resource should not be processed by content filter.", cf.shouldProcessURL(null, "/path/subpath/test.jpg"));
        assertFalse("URLs containing org.sparkcommerce.admin should not be processed.", cf.shouldProcessURL(null, "/path/org.sparkcommerce.admin/admintest"));
        assertTrue("/about_us should be processed by the content filter", cf.shouldProcessURL(null, "/about_us"));
        assertTrue("*.htm resources should be processed by the content filter", cf.shouldProcessURL(null, "/test.htm"));
    }
}
