/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web.util;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * Code is largely copied from StackOverflow post made by David Rabinowitz with contributions
 * by others in the same thread.   Overrides all status setting methods and retains the status.
 * <br><br>
 *
 * http://stackoverflow.com/questions/1302072/how-can-i-get-the-http-status-code-out-of-a-servletresponse-in-a-servletfilter<br><br>
 *
 * This won't be needed with Servlet 3.0.<br><br>
 *
 * @author : Adasari
 */
public class StatusExposingServletResponse extends HttpServletResponseWrapper {

    private int httpStatus=200;

    public StatusExposingServletResponse(HttpServletResponse response) {
        super(response);
    }

    @Override
    public void sendError(int sc) throws IOException {
        httpStatus = sc;
        super.sendError(sc);
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        httpStatus = sc;
        super.sendError(sc, msg);
    }

    @Override
    public void setStatus(int sc) {
        httpStatus = sc;
        super.setStatus(sc);
    }

    @Override
    public void reset() {
        super.reset();
        this.httpStatus = SC_OK;
    }

    @Override
    public void setStatus(int status, String string) {
        super.setStatus(status, string);
        this.httpStatus = status;
    }

    public int getStatus() {
        return httpStatus;
    }

}
