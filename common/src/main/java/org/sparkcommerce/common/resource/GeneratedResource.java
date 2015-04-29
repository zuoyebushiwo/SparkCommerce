/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.AbstractResource;
import org.springframework.security.util.InMemoryResource;
import org.springframework.util.Assert;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * An in memory generated resource. This class also overrides some parent Spring AbstractResource methods to ensure
 * compatibility with the {@link ResourceHttpRequestHandler}.
 * 
 * Note that this class <i>intentionally</i> does not subclass Spring's {@link InMemoryResource} and instead has copied
 * the fields here because {@link InMemoryResource} does not provide a default constructor. This causes issues when
 * deserializing an instance from disk (such as in a caching scenario that overflows from memory to disk).
 * 
 * @author Anand Dasari
 */
public class GeneratedResource extends AbstractResource implements Serializable {
    
    private static final long serialVersionUID = 7044543270746433688L;

    protected long timeGenerated;
    protected String hashRepresentation;
    
    protected final byte[] source;
    protected final String description;

    /**
     * <b>Note: This constructor should not be explicitly used</b> 
     * 
     * To properly allow for serialization, we must provide this no-arg constructor that will 
     * create a "dummy" GeneratedResource. The appropriate fields will be set during deserialization.
     */
    public GeneratedResource()  {
        this(new byte[]{}, null);
    }

    public GeneratedResource(byte[] source, String description) {
        Assert.notNull(source);
        this.source = source;
        this.description = description;
        timeGenerated = System.currentTimeMillis();
    }
    
    @Override
    public String getFilename() {
        return getDescription();
    }
    
    @Override
	public long lastModified() throws IOException {
        return timeGenerated;
    }
    
    public String getHashRepresentation() {
        return StringUtils.isBlank(hashRepresentation) ? String.valueOf(timeGenerated) : hashRepresentation;
    }

    public void setHashRepresentation(String hashRepresentation) {
        this.hashRepresentation = hashRepresentation;
    }
    
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(source);
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object res) {
        if (res == null) return false;
        if (!getClass().isAssignableFrom(res.getClass())) {
            return false;
        }

        return Arrays.equals(source, ((GeneratedResource)res).source);
    }

}
