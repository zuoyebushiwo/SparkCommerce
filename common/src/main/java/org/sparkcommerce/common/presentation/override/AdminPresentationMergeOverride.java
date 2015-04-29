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
 * @author Anand Dasari
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AdminPresentationMergeOverride {

    /**
     * The name of the property whose admin presentation annotation should be overwritten
     *
     * @return the name of the property that should be overwritten
     */
    String name();

    /**
     * The array of override configuration values. Each entry correlates to a property on
     * {@link org.sparkcommerce.common.presentation.AdminPresentation},
     * {@link org.sparkcommerce.common.presentation.AdminPresentationToOneLookup},
     * {@link org.sparkcommerce.common.presentation.AdminPresentationDataDrivenEnumeration},
     * {@link org.sparkcommerce.common.presentation.AdminPresentationAdornedTargetCollection},
     * {@link org.sparkcommerce.common.presentation.AdminPresentationCollection} or
     * {@link org.sparkcommerce.common.presentation.AdminPresentationMap}
     *
     * @return The array of override configuration values.
     */
    AdminPresentationMergeEntry[] mergeEntries();
}
