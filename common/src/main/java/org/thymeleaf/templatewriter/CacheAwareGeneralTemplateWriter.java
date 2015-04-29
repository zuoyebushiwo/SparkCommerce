/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */

package org.thymeleaf.templatewriter;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Node;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Wrapper for Thymeleaf's {@link AbstractGeneralTemplateWriter} that provides content caching
 * on the node level.
 * 
 * @author Anand Dasari
 */
public class CacheAwareGeneralTemplateWriter extends AbstractGeneralTemplateWriter {

    protected static final Log LOG = LogFactory.getLog(CacheAwareGeneralTemplateWriter.class);

    protected Cache cache;
    protected AbstractGeneralTemplateWriter delegateWriter;

    public CacheAwareGeneralTemplateWriter(AbstractGeneralTemplateWriter delegateWriter) {
        this.delegateWriter = delegateWriter;
    }
    
    @Override
    public void writeNode(final Arguments arguments, final Writer writer, final Node node) 
            throws IOException {
        if (!(node instanceof org.thymeleaf.dom.Element)) {
            super.writeNode(arguments, writer, node);
            return;
        }
        
        org.thymeleaf.dom.Element e = (org.thymeleaf.dom.Element) node;
        
        String cacheKey = (String) e.getNodeProperty("cacheKey");
        
        if (StringUtils.isNotBlank(cacheKey)) {
            String valueToWrite = (String) e.getNodeProperty("blCacheResponse");
            
            if (valueToWrite != null) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("Read template from cache - " + cacheKey);
                }
            } else {
                Boolean outputParentNode = (Boolean) e.getNodeProperty("blcOutputParentNode");
                StringWriter w2 = new StringWriter();

                if (Boolean.TRUE.equals(outputParentNode)) {
                    super.writeNode(arguments, w2, e);
                } else {
                    final Node[] children = e.unsafeGetChildrenNodeArray();
                    final int childrenLen = e.numChildren();
                    for (int i = 0; i < childrenLen; i++) {
                        super.writeNode(arguments, w2, children[i]);
                    }
                }

                valueToWrite = w2.toString();

                Element element = new Element(cacheKey, valueToWrite);
                getCache().put(element);
            }
            
            writer.write(valueToWrite);
        } else {
            super.writeNode(arguments, writer, node);
        }
    }

    @Override
    protected boolean shouldWriteXmlDeclaration() {
        return delegateWriter.shouldWriteXmlDeclaration();
    }

    @Override
    protected boolean useXhtmlTagMinimizationRules() {
        return delegateWriter.useXhtmlTagMinimizationRules();
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public Cache getCache() {
        if (cache == null) {
            cache = CacheManager.getInstance().getCache("blTemplateElements");
        }
        return cache;
    }

}
