/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.enumeration.dao;

import org.sparkcommerce.common.enumeration.domain.DataDrivenEnumeration;
import org.sparkcommerce.common.enumeration.domain.DataDrivenEnumerationValue;

public interface DataDrivenEnumerationDao {

    public DataDrivenEnumeration readEnumByKey(String enumKey);

    public DataDrivenEnumerationValue readEnumValueByKey(String enumKey, String enumValueKey);

}
