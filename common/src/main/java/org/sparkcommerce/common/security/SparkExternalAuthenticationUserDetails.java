/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * This is an extension of Spring's User class to provide additional data to the UserDetails interface.  This should be used by derivitave
 * authentication providers to return an instance of UserDetails when authenticating against a system other than the Spark tables (e.g. LDAP)
 * <p/>
 * User: Kelly Tisdell
 * Date: 6/19/12
 */
public class SparkExternalAuthenticationUserDetails extends User {
    
    private String firstName;
    
    private String lastName;
    
    private String email;
    
    public SparkExternalAuthenticationUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
