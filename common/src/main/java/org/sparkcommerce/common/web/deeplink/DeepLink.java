/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.web.deeplink;

/**
 * DTO Class that contains enough information to allow the client site application to generate
 * the necessary information for a link to an admin screen
 * 
 * @author Anand Dasari
 */
public class DeepLink {

    protected String adminBaseUrl;
    protected String urlFragment;
    protected String displayText;
    protected Object sourceObject;

    /* ******************* *
     * WITH FLUID BUILDERS *
     * ******************* */
    
    public DeepLink withAdminBaseUrl(String adminBaseUrl) {
        setAdminBaseUrl(adminBaseUrl);
        return this;
    }

    public DeepLink withUrlFragment(String urlFragment) {
        setUrlFragment(urlFragment);
        return this;
    }

    public DeepLink withDisplayText(String displayText) {
        setDisplayText(displayText);
        return this;
    }
    
    public DeepLink withSourceObject(Object sourceObject) {
        setSourceObject(sourceObject);
        return this;
    }

    /* ************************ *
     * CUSTOM GETTERS / SETTERS *
     * ************************ */

    public void setAdminBaseUrl(String adminBaseUrl) {
        if (adminBaseUrl.charAt(adminBaseUrl.length() - 1) == '/') {
            adminBaseUrl = adminBaseUrl.substring(0, adminBaseUrl.length() - 1);
        }
        this.adminBaseUrl = adminBaseUrl;
    }

    public void setUrlFragment(String urlFragment) {
        if (urlFragment.charAt(0) == '/') {
            urlFragment = urlFragment.substring(1);
        }
        this.urlFragment = urlFragment;
    }
    
    public String getFullUrl() {
        return adminBaseUrl + "/" + urlFragment;
    }

    /* ************************* *
     * GENERIC GETTERS / SETTERS *
     * ************************* */

    public String getAdminBaseUrl() {
        return adminBaseUrl;
    }

    public String getUrlFragment() {
        return urlFragment;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }
    
    public Object getSourceObject() {
        return sourceObject;
    }
    
    public void setSourceObject(Object sourceObject) {
        this.sourceObject = sourceObject;
    }
    
}
