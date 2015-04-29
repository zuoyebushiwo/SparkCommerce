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
(function($, SCAdmin) {
    
    SCAdmin.translations = {
        getProperties : function($container) {
            return {
                ceilingEntity : $container.find('.translation-ceiling').text(),
                entityId      : $container.find('.translation-id').text(),
                propertyName  : $container.find('.translation-property').text(),
                isRte         : $container.find('.translation-rte').text()
            };
        }
    };
    
})(jQuery, SCAdmin);

$(document).ready(function() {
    
    $('body').on('click', 'a.show-translations', function() {
        if ($(this).data('disabled') != 'disabled') {
            SCAdmin.showLinkAsModal($(this).attr('href'));
        }
    	return false;
    });
    
    $('body').on('click', 'button.translation-submit-button', function() {
	    var $form = $(this).closest('.modal').find('.modal-body form');
		
		SC.ajax({
			url: $form.attr('action'),
			type: "POST",
			data: $form.serialize()
		}, function(data) {
            SCAdmin.listGrid.replaceRelatedListGrid($(data));
            SCAdmin.hideCurrentModal();
	    });
		
		return false;
    });
    
    $('body').on('click', 'button.translation-grid-add', function() {
        var $container = $(this).closest('.listgrid-container');
        var baseUrl = $container.find('.listgrid-header-wrapper table').data('currenturl');
        var properties = SCAdmin.translations.getProperties($container);
        
        SCAdmin.showLinkAsModal(baseUrl + '/add?' + $.param(properties));
        return false;
    });
    
    $('body').on('click', 'button.translation-grid-update', function() {
        var $container = $(this).closest('.listgrid-container');
        var $selectedRows = $container.find('table tr.selected');
        var baseUrl = $container.find('.listgrid-header-wrapper table').data('currenturl');
        var rowFields = SCAdmin.listGrid.getRowFields($selectedRows);
        var properties = SCAdmin.translations.getProperties($container);
        
        properties.localeCode = rowFields.localeCode;
        properties.translationId = rowFields.id;
        
        SCAdmin.showLinkAsModal(baseUrl + '/update?' + $.param(properties));
        return false;
    });
    
    $('body').on('click', 'button.translation-grid-remove', function() {
        var $container = $(this).closest('.listgrid-container');
        var $selectedRows = $container.find('table tr.selected');
        var baseUrl = $container.find('.listgrid-header-wrapper table').data('currenturl');
        var rowFields = SCAdmin.listGrid.getRowFields($selectedRows);
        var properties = SCAdmin.translations.getProperties($container);
        
        properties.translationId = rowFields.id;
        
        SC.ajax({
            url: baseUrl + '/delete',
            data: properties,
            type: "POST"
        }, function(data) {
            SCAdmin.listGrid.replaceRelatedListGrid($(data));
        });
        
        return false;
    });

});
