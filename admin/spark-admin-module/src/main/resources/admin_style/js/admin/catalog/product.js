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
(function($, SCAdmin) {
    
    // Add utility functions for products to the SCAdmin object
    SCAdmin.product = {
            
        refreshSkusGrid : function($container, listGridUrl) {
            SC.ajax({
                url : listGridUrl,
                type : "GET"
            }, function(data) {
                SCAdmin.listGrid.replaceRelatedListGrid($(data));
            });
        }

    };
    
})(jQuery, SCAdmin);

$(document).ready(function() {
    
    $('body').on('click', 'button.generate-skus', function() {
        var $container = $(this).closest('div.listgrid-container');
        
        SC.ajax({
            url : $(this).data('actionurl'),
            type : "GET"
        }, function(data) {
            var alertType = data.error ? 'alert' : '';
            
            SCAdmin.listGrid.showAlert($container, data.message, {
                alertType: alertType,
                clearOtherAlerts: true
            });
            
            if (data.skusGenerated > 0) {
                SCAdmin.product.refreshSkusGrid($container, data.listGridUrl);
            }
        });
        
        return false;
    });
});
