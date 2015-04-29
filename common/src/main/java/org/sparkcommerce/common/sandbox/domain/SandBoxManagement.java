/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.sandbox.domain;

import java.io.Serializable;

/**
 * @author Joel Dasari
 */
public interface SandBoxManagement extends Serializable {

    SandBox getSandBox();

    void setSandBox(SandBox sandBox);
}
