/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.config.service;

import org.sparkcommerce.common.config.domain.ModuleConfiguration;
import org.sparkcommerce.common.config.service.type.ModuleConfigurationType;

import java.util.List;

public interface ModuleConfigurationService {

    public ModuleConfiguration findById(Long id);

    public ModuleConfiguration save(ModuleConfiguration config);

    public void delete(ModuleConfiguration config);

    public List<ModuleConfiguration> findActiveConfigurationsByType(ModuleConfigurationType type);

    public List<ModuleConfiguration> findAllConfigurationByType(ModuleConfigurationType type);

    public List<ModuleConfiguration> findByType(Class<? extends ModuleConfiguration> type);

}
