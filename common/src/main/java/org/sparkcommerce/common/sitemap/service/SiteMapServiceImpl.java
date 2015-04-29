/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */

package org.sparkcommerce.common.sitemap.service;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sparkcommerce.common.config.domain.ModuleConfiguration;
import org.sparkcommerce.common.config.service.ModuleConfigurationService;
import org.sparkcommerce.common.config.service.type.ModuleConfigurationType;
import org.sparkcommerce.common.file.domain.FileWorkArea;
import org.sparkcommerce.common.file.service.SparkFileService;
import org.sparkcommerce.common.sitemap.domain.SiteMapConfiguration;
import org.sparkcommerce.common.sitemap.domain.SiteMapGeneratorConfiguration;
import org.sparkcommerce.common.sitemap.exception.SiteMapException;
import org.sparkcommerce.common.util.SCSystemProperty;
import org.sparkcommerce.common.web.BaseUrlResolver;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import javax.annotation.Resource;

/**
 * Component responsible for generating a sitemap.   Relies on SiteMapGenerators to 
 * produce the actual url entries within the sitemap.
 * 
 * Create a sitemap index file and at least one sitemap file with the URL elements.
 * 
 * @author jdasari
 *
 */
@Service("blSiteMapService")
public class SiteMapServiceImpl implements SiteMapService {

    protected static final Log LOG = LogFactory.getLog(SiteMapServiceImpl.class);

    protected Boolean gzipSiteMapFiles;

    @Resource(name = "blModuleConfigurationService")
    protected ModuleConfigurationService moduleConfigurationService;

    @Resource(name = "blSiteMapGenerators")
    protected List<SiteMapGenerator> siteMapGenerators = new ArrayList<SiteMapGenerator>();

    @Resource(name = "blFileService")
    protected SparkFileService sparkFileService;

    @Resource(name = "blBaseUrlResolver")
    protected BaseUrlResolver baseUrlResolver;

    @Override
    public SiteMapGenerationResponse generateSiteMap() throws SiteMapException, IOException {
        SiteMapGenerationResponse smgr = new SiteMapGenerationResponse();
        SiteMapConfiguration smc = findActiveSiteMapConfiguration();
        if (smc == null) {
            if (LOG.isWarnEnabled()) {
                LOG.warn("No SiteMap generated since no active configuration was found.");
            }
            smgr.setHasError(true);
            smgr.setErrorCode("No SiteMap Configuration Found");
            return smgr;
        }

        FileWorkArea fileWorkArea = sparkFileService.initializeWorkArea();
        SiteMapBuilder siteMapBuilder = new SiteMapBuilder(smc, fileWorkArea, baseUrlResolver.getSiteBaseUrl(), getGzipSiteMapFiles());

        if (LOG.isTraceEnabled()) {
            LOG.trace("File work area initalized with path " + fileWorkArea.getFilePathLocation());
        }
        for (SiteMapGeneratorConfiguration currentConfiguration : smc.getSiteMapGeneratorConfigurations()) {
            if (currentConfiguration.isDisabled()) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("Skipping disabled sitemap generator configuration" + currentConfiguration.getId());
                }
                continue;
            }
            SiteMapGenerator generator = selectSiteMapGenerator(currentConfiguration);
            if (generator != null) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("SiteMapGenerator found, adding entries" + generator.getClass());
                }
                generator.addSiteMapEntries(currentConfiguration, siteMapBuilder);
            } else {
                LOG.warn("No site map generator found to process generator configuration for " + currentConfiguration.getSiteMapGeneratorType());
            }
        }

        siteMapBuilder.persistSiteMap();
        if (getGzipSiteMapFiles()) {
            gzipAndDeleteFiles(fileWorkArea, siteMapBuilder.getIndexedFileNames());
        }


        // Move the generated files to their permanent location
        sparkFileService.addOrUpdateResources(fileWorkArea, true);
        sparkFileService.closeWorkArea(fileWorkArea);

        return smgr;
    }

    @Override
    public File getSiteMapFile(String fileName) throws SiteMapException, IOException {
        if (LOG.isTraceEnabled()) {
            LOG.trace("Method getSiteMapFile() invoked for " + fileName);
        }
        File siteMapFile = sparkFileService.getResource(fileName, getSiteMapTimeoutInMillis());
        if (siteMapFile.exists()) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Returning existing SiteMap");
            }
            return siteMapFile;
        } else {
            if (getCreateSiteMapIfNotFound()) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("Generating SiteMap");
                }
                generateSiteMap();
                siteMapFile = sparkFileService.getResource(fileName, getSiteMapTimeoutInMillis());
                if (siteMapFile.exists()) {
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("Returning SiteMap file " + fileName);
                    }
                } else {
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("Sitemap file " + fileName + " not found after call to generate siteMap.xml");
                    }
                }
                return siteMapFile;
            } else {
                return null;
            }
        }        
    }

    protected SiteMapConfiguration findActiveSiteMapConfiguration() {
        List<ModuleConfiguration> configurations = moduleConfigurationService.findActiveConfigurationsByType(ModuleConfigurationType.SITE_MAP);

        SiteMapConfiguration smc = null;
        if (configurations != null && !configurations.isEmpty()) {
            //Try to find a default configuration           
            for (ModuleConfiguration configuration : configurations) {
                if (configuration.getIsDefault()) {
                    smc = (SiteMapConfiguration) configuration;
                    break;
                }
            }
            if (smc == null) {
                //if there wasn't a default one, use the first active one...
                smc = (SiteMapConfiguration) configurations.get(0);
            }
        }

        return smc;
    }

    /**
     * Returns the siteMapGenerator most qualified to handle the given configuration.     
     * 
     * @param smgc
     * @return
     */
    protected SiteMapGenerator selectSiteMapGenerator(SiteMapGeneratorConfiguration smgc) {
        for (SiteMapGenerator siteMapGenerator : siteMapGenerators) {
            if (siteMapGenerator.canHandleSiteMapConfiguration(smgc)) {
                return siteMapGenerator;
            }
        }
        return null;
    }

    /**
     * Gzip a file and then delete the file
     * 
     * @param fileName
     */
    protected void gzipAndDeleteFiles(FileWorkArea fileWorkArea, List<String> fileNames) {
        for (String fileName : fileNames) {
            try {
                String fileNameWithPath = FilenameUtils.normalize(fileWorkArea.getFilePathLocation() + File.separator + fileName);

                FileInputStream fis = new FileInputStream(fileNameWithPath);
                FileOutputStream fos = new FileOutputStream(fileNameWithPath + ".gz");
                GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) != -1) {
                    gzipOS.write(buffer, 0, len);
                }
                //close resources
                gzipOS.close();
                fos.close();
                fis.close();

                File originalFile = new File(fileNameWithPath);
                originalFile.delete();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<SiteMapGenerator> getSiteMapGenerators() {
        return siteMapGenerators;
    }

    public void setSiteMapGenerators(List<SiteMapGenerator> siteMapGenerators) {
        this.siteMapGenerators = siteMapGenerators;
    }

    public ModuleConfigurationService getModuleConfigurationService() {
        return moduleConfigurationService;
    }

    public void setModuleConfigurationService(ModuleConfigurationService moduleConfigurationService) {
        this.moduleConfigurationService = moduleConfigurationService;
    }

    protected boolean getGzipSiteMapFilesDefault() {
        return SCSystemProperty.resolveBooleanSystemProperty("sitemap.gzip.files");
    }

    public boolean getCreateSiteMapIfNotFound() {
        return SCSystemProperty.resolveBooleanSystemProperty("sitemap.createIfNotFound");
    }

    public Long getSiteMapTimeoutInMillis() {
        Long cacheSeconds = SCSystemProperty.resolveLongSystemProperty("sitemap.cache.seconds");
        return cacheSeconds * 1000;
    }


    public void setGzipSiteMapFiles(Boolean gzipSiteMapFiles) {
        this.gzipSiteMapFiles = gzipSiteMapFiles;
    }

    public boolean getGzipSiteMapFiles() {
        if (this.gzipSiteMapFiles != null) {
            return this.gzipSiteMapFiles.booleanValue();
        } else {
            return getGzipSiteMapFilesDefault();
        }
    }
}
