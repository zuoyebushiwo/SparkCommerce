/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web;

import org.springframework.web.context.request.WebRequest;

import java.util.TimeZone;

/**
 * Responsible for returning the TimeZone to use for the current request.
 *
 * @author Anand Dasari
 */
public interface SparkTimeZoneResolver  {


    public TimeZone resolveTimeZone(WebRequest request);

}
