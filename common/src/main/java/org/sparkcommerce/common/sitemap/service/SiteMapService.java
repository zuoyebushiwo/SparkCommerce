/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */

package org.sparkcommerce.common.sitemap.service;

import org.sparkcommerce.common.sitemap.exception.SiteMapException;

import java.io.File;
import java.io.IOException;

/**
 * Class responsible for generating the SiteMap.xml and related files.  
 * 
 * This service generates the structure of the SiteMap file.  It assumes the use of SiteMap indexes 
 * and follows the convention siteMap#.xml 
 *
 * @author jdasari
 * 
 */
public interface SiteMapService {

    /**
     * Generates a well formed SiteMap.   When {@link #getSiteMapFile(String)} is called, if no file is found then
     * it will invoke this method.    Typically, an implementation will setup scheduled jobs to create the
     * siteMap.xml.
     * 
     * Implementation should implement a well formed SiteMap (for example, the default Spark SiteMapImpl
     * returns a SiteMap compatible with this schema.  
     * 
     * http://www.sitemaps.org/schemas/sitemap/0.9/siteindex.xsd
     * 
     * Implementations should utilize the list of SiteMapGenerators that build the actual entries in the sitemap.xml
     * files.
     * @throws SiteMapException 
     * 
     * @see SiteMapGenerator
     */
    SiteMapGenerationResponse generateSiteMap() throws IOException, SiteMapException;

    /**
     * Returns the File object that can be used to retrieve the SiteMap.xml file
     * @throws IOException 
     * @throws SiteMapException 
     */
    File getSiteMapFile(String fileName) throws SiteMapException, IOException;
}
