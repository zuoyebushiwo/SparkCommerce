/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.encryption;

public interface EncryptionModule {

    public String encrypt(String plainText);

    public String decrypt(String cipherText);

}
