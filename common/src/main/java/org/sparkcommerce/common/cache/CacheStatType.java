/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.cache;

/**
 * @author Jeff Fischer
 */
public enum CacheStatType {
    PAGE_CACHE_HIT_RATE, STRUCTURED_CONTENT_CACHE_HIT_RATE, URL_HANDLER_CACHE_HIT_RATE,
    PRODUCT_URL_MISSING_CACHE_HIT_RATE, CATEGORY_URL_MISSING_CACHE_HIT_RATE, TRANSLATION_CACHE_HIT_RATE,
    RESOURCE_BUNDLING_CACHE_HIT_RATE,GENERATED_RESOURCE_CACHE_HIT_RATE
}
