/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web.resource;

import org.apache.commons.lang3.StringUtils;
import org.sparkcommerce.common.config.RuntimeEnvironmentPropertiesManager;
import org.sparkcommerce.common.resource.GeneratedResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Component("blSCJSResourceHandler")
public class SCJSResourceHandler extends AbstractGeneratedResourceHandler {
    
    @Autowired
    protected RuntimeEnvironmentPropertiesManager propMgr;

    @Override
    public boolean canHandle(String path) {
        return path.endsWith("SC.js");
    }

    @Override
    public Resource getFileContents(String path, List<Resource> locations) {
        Resource resource = getRawResource(path, locations);
    	String contents;
        try {
            contents = getResourceContents(resource);
        } catch (IOException e) {
            throw new RuntimeException("Could not get raw resource contents", e);
        }
        
        String newContents = contents;
        if (StringUtils.isNotBlank(contents)) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	        newContents = newContents.replace("//SC-SERVLET-CONTEXT", request.getContextPath());

	        String siteBaseUrl = propMgr.getProperty("site.baseurl");
	        if (StringUtils.isNotBlank(siteBaseUrl)) {
	            newContents = newContents.replace("//SC-SITE-BASEURL", siteBaseUrl);
	        }
        }
	    
        GeneratedResource gr = new GeneratedResource(newContents.getBytes(), path);
        gr.setHashRepresentation(String.valueOf(contents.hashCode()));
        return gr;
    }

    @Override
    public boolean isCachedResourceExpired(GeneratedResource cachedResource, String path, List<Resource> locations) {
        String contents;
        
        try {
            contents = getResourceContents(getRawResource(path, locations));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        return !cachedResource.getHashRepresentation().equals(String.valueOf(contents.hashCode()));
    }

}
