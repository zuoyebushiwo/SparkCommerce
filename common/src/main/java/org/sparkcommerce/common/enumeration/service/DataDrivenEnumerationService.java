/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.enumeration.service;

import org.sparkcommerce.common.enumeration.domain.DataDrivenEnumeration;
import org.sparkcommerce.common.enumeration.domain.DataDrivenEnumerationValue;

public interface DataDrivenEnumerationService {

    public DataDrivenEnumeration findEnumByKey(String enumKey);

    public DataDrivenEnumerationValue findEnumValueByKey(String enumKey, String enumValueKey);

}
