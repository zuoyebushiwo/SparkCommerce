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
package org.sparkcommerce.openadmin.web.form.entity;



public class DefaultEntityFormActions {
    
    public static final EntityFormAction SAVE = new EntityFormAction(EntityFormAction.SAVE)
        .withButtonType("submit")
        .withButtonClass("submit-button")
        .withDisplayText("Save");

    public static final EntityFormAction DELETE = new EntityFormAction(EntityFormAction.DELETE)
        .withButtonClass("delete-button alert")
        .withDisplayText("Delete");
    
    public static final EntityFormAction PREVIEW = new EntityFormAction(EntityFormAction.PREVIEW)
        .withButtonClass("preview-button")
        .withDisplayText("Preview");

}
