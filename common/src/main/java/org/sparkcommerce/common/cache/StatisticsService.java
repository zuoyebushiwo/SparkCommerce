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
public interface StatisticsService {
    void addCacheStat(String key, boolean isHit);

    Long getLogResolution();

    void setLogResolution(Long logResolution);

    void activateLogging();

    void disableLogging();
}
