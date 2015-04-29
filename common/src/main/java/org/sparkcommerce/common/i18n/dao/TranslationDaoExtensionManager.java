/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.i18n.dao;

import org.sparkcommerce.common.extension.ExtensionManager;
import org.springframework.stereotype.Service;


/**
 * @author Anand Dasari
 */
@Service("blTranslationDaoExtensionManager")
public class TranslationDaoExtensionManager extends ExtensionManager<TranslationDaoExtensionHandler> {

    public TranslationDaoExtensionManager() {
        super(TranslationDaoExtensionHandler.class);
    }

    /**
     * By default,this extension manager will continue on handled allowing multiple handlers to interact with the order.
     */
    public boolean continueOnHandled() {
        return true;
    }
}
