/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.presentation.client;

/**
 * 
 * @author JDasari
 *
 */
public enum SupportedFieldType {
    UNKNOWN,
    ID,
    BOOLEAN,
    DATE,
    INTEGER,
    DECIMAL,
    STRING,
    PASSWORD,
    PASSWORD_CONFIRM,
    EMAIL,
    FOREIGN_KEY,
    ADDITIONAL_FOREIGN_KEY,
    MONEY,
    BROADLEAF_ENUMERATION,
    EXPLICIT_ENUMERATION,
    EMPTY_ENUMERATION,
    DATA_DRIVEN_ENUMERATION,
    HTML,
    HTML_BASIC,
    UPLOAD,
    HIDDEN,
    ASSET_URL,
    ASSET_LOOKUP,
    MEDIA,
    RULE_SIMPLE,
    RULE_WITH_QUANTITY,
    STRING_LIST,
    IMAGE,
    COLOR,
    CODE
}
