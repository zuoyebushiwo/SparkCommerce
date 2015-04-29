/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.resource.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.sparkcommerce.common.util.SCSystemProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

/**
 * @see ResourceMinificationService 
 * @author Anand Dasari
 */
@Service("blResourceMinificationService")
public class ResourceMinificationServiceImpl implements ResourceMinificationService {
    protected static final Log LOG = LogFactory.getLog(ResourceMinificationServiceImpl.class);

    @Value("${minify.linebreak}")
    protected int linebreak;
    
    @Value("${minify.munge}")
    protected boolean munge;
    
    @Value("${minify.verbose}")
    protected boolean verbose;
    
    @Value("${minify.preserveAllSemiColons}")
    protected boolean preserveAllSemiColons;
    
    @Value("${minify.disableOptimizations}")
    protected boolean disableOptimizations;

    @Override
    public boolean getEnabled() {
        return SCSystemProperty.resolveBooleanSystemProperty("minify.enabled");
    }

    @Override
    public boolean getAllowSingleMinification() {
        return SCSystemProperty.resolveBooleanSystemProperty("minify.allowSingleMinification");
    }
    
    @Override
    public byte[] minify(String filename, byte[] bytes) {
        if (!getEnabled()) {
            return bytes;
        }
        
        String type = null;
        if (filename.endsWith(".js")) {
            type = "js";
        } else if (filename.endsWith(".css")) {
            type = "css";
        }
        
        if (!"js".equals(type) && !"css".equals(type)) {
            throw new IllegalArgumentException("Can only minify js or css resources");
        }
        
        byte[] minifiedBytes;
        
        BufferedReader in = null;
        BufferedWriter out = null;
        try {
            // Input streams to read the bytes
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            InputStreamReader isr = new InputStreamReader(bais, "utf-8");
            in = new BufferedReader(isr);

            // Output streams to save the modified bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(baos, "utf-8");
            out = new BufferedWriter(osw);

            if ("js".equals(type)) {
                JavaScriptCompressor jsc = new JavaScriptCompressor(in, getLogBasedErrorReporter());
                jsc.compress(out, linebreak, munge, verbose, preserveAllSemiColons, disableOptimizations);
            } else if ("css".equals(type)) {
                CssCompressor cssc = new CssCompressor(in);
                cssc.compress(out, 100);
            }
            out.flush();
            minifiedBytes = baos.toByteArray();
        } catch (Exception e) { // Catch everything - on a runtime exception, we still want to return the unminified bytes
            LOG.warn("Could not minify resources, returned unminified bytes", e);
            return bytes;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        }
        
        return minifiedBytes;
    }
    
    protected ErrorReporter getLogBasedErrorReporter() {
        return new ErrorReporter() {
            @Override
            public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
                if (line < 0) {
                    LOG.warn(message);
                } else {
                    LOG.warn(line + ':' + lineOffset + ':' + message);
                }
            }

            @Override
            public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {
                if (line < 0) {
                    LOG.error(message);
                } else {
                    LOG.error(line + ':' + lineOffset + ':' + message);
                }
            }

            @Override
            public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource, 
                    int lineOffset) {
                error(message, sourceName, line, lineSource, lineOffset);
                return new EvaluatorException(message);
            }
            
        };
    }

}
