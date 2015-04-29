/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.extension;



/**
 * <p>An extension handler represents a generic pattern used in SparkCommerce when an out-of-box service
 * with complex logic provides implementation hooks.</p>
 * 
 * <p>The pattern is primarily used internally by Spark as a mechanism to provide extension points for 
 * Spark modules.</p>
 * 
 * <p>Consumers of SparkCommerce framework typically would not need to use this pattern and instead would opt. 
 * for more typical extension patterns including overriding or extending the actual component for which 
 * alternate behavior is desired.</p>
 * 
 * <p>ExtensionHandler api methods should always return an instance of {@link ExtensionResultStatusType} and will usually
 * extend from {@link AbstractExtensionHandler}</p>
 * 
 * <p>In order to associate an {@link ExtensionHandler) with an {@link ExtensionManager}, each handler should have an @PostConstruct
 * override and associate itself with the manager:</p>
 * 
 * <pre>
 *  {@code
 *    {@literal @}Resource(name = "blSomeExtensionManager")
 *    protected ExtensionManager extensionManager;
 *
 *    {@literal @}PostConstruct
 *    public void init() {
 *       if (isEnabled()) {
 *           extensionManager.registerHandler(this);
 *       }
 *    }
 *  }
 * </pre>
 * 
 * 
 * @author bpolster
 * @see {@link AbstractExtensionHandler}
 */
public interface ExtensionHandler {

    /**
     * Determines the priority of this extension handler.
     * @return
     */
    public int getPriority();

    /**
     * If false, the ExtensionManager should skip this Handler.
     * @return
     */
    public boolean isEnabled();
}
