/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.presentation.override;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows a non-comprehensive override of admin presentation annotation
 * property values for a target entity field.
 *
 * @see org.sparkcommerce.common.presentation.AdminPresentation
 * @see org.sparkcommerce.common.presentation.AdminPresentationToOneLookup
 * @see org.sparkcommerce.common.presentation.AdminPresentationDataDrivenEnumeration
 * @see org.sparkcommerce.common.presentation.AdminPresentationCollection
 * @see org.sparkcommerce.common.presentation.AdminPresentationAdornedTargetCollection
 * @see org.sparkcommerce.common.presentation.AdminPresentationMap
 *
 *
 * @author Anand Dasari
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AdminPresentationMergeOverrides {

    /**
     * The new configurations for each field targeted for override
     *
     * @return field specific overrides
     */
    AdminPresentationMergeOverride[] value();

}
