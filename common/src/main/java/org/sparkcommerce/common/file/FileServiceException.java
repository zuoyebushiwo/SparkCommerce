/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.file;

import org.sparkcommerce.common.file.service.SparkFileService;

/**
 * Marker exception that just extends RuntimeException to be used by the {@link SparkFileService}
 * @author jdasari
 *
 */
public class FileServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FileServiceException() {
        super();
    }

    public FileServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileServiceException(String message) {
        super(message);
    }

    public FileServiceException(Throwable cause) {
        super(cause);
    }
}
