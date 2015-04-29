/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.vendor.service.message;

import java.io.Serializable;

public interface TaxResponse extends Serializable {

    public boolean isErrorDetected();

    public void setErrorDetected(boolean isErrorDetected);

    public String getErrorCode();

    public void setErrorCode(String errorCode);

    public String getErrorText();

    public void setErrorText(String errorText);

}
