/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.dialect;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * Intended to allow installations migrating from SC version 2.0 to not be forced to make a schema
 * change for boolean fields when migrating to SC version 3.0, and above.
 *
 * @deprecated use org.hibernate.dialect.MySQL5InnoDBDialect instead
 * @author Jeff Fischer
 */
@Deprecated
public class Spark2CompatibilityMySQL5InnoDBDialect extends MySQL5InnoDBDialect {

    public Spark2CompatibilityMySQL5InnoDBDialect() {
        super();
        registerColumnType( java.sql.Types.BOOLEAN, "bit" );
    }

}
