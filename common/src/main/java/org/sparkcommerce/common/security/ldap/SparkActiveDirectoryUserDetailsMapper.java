/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.security.ldap;

import org.sparkcommerce.common.security.SparkExternalAuthenticationUserDetails;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 * This class allows Spring to do it's thing with respect to mapping user details from
 * LDAP to the Spring's security framework. However, this class allows us to specify whether
 * to use the user's user name from LDAP, or to use their email address to map them to a Spark
 * user.  It also allows us to override the role names (GrantedAuthorities) that come from LDAP with
 * names that may be more suitable for Spark.
 *
 * @deprecated NO LONGER REQUIRED AND SHOULD NOT BE USED. SEE SparkAdminLdapUserDetailsMapper.
 *
 * @author Anand Dasari
 *
 */
@Deprecated
public class SparkActiveDirectoryUserDetailsMapper extends LdapUserDetailsMapper {

    protected boolean useEmailAddressAsUsername = true;

    protected boolean additiveRoleNameSubstitutions = false;

    protected Map<String, String[]> roleNameSubstitutions;

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
        Collection<GrantedAuthority> newAuthorities = new HashSet<GrantedAuthority>();

        if (roleNameSubstitutions != null && ! roleNameSubstitutions.isEmpty()) {
            for (GrantedAuthority authority : authorities) {
                if (roleNameSubstitutions.containsKey(authority.getAuthority())) {
                    String[] roles = roleNameSubstitutions.get(authority.getAuthority());
                    for (String role : roles) {
                        newAuthorities.add(new SimpleGrantedAuthority(role.trim()));
                    }
                    if (additiveRoleNameSubstitutions) {
                        newAuthorities.add(authority);
                    }
                } else {
                    newAuthorities.add(authority);
                }
            }
        } else {
            newAuthorities.addAll(authorities);
        }

        String email = (String)ctx.getObjectAttribute("mail");
        UserDetails userDetails = null;
        if (useEmailAddressAsUsername) {
            if (email != null) {
                userDetails = super.mapUserFromContext(ctx, email, newAuthorities);
            }
        }

        if (userDetails == null) {
            userDetails = super.mapUserFromContext(ctx, username, newAuthorities);
        }
        
        String password = userDetails.getPassword();
        if (password == null) {
            password = userDetails.getUsername();
        }

        SparkExternalAuthenticationUserDetails sparkUser = new SparkExternalAuthenticationUserDetails(userDetails.getUsername(), password, userDetails.getAuthorities());
        sparkUser.setFirstName((String)ctx.getObjectAttribute("givenName"));
        sparkUser.setLastName((String)ctx.getObjectAttribute("sn"));
        sparkUser.setEmail(email);

        return sparkUser;
    }

    /**
     * The LDAP server may contain a user name other than an email address.  If the email address should be used to map to a Spark user, then
     * set this to true.  The principal will be set to the user's email address returned from the LDAP server.
     * @param value
     */
    public void setUseEmailAddressAsUsername(boolean value) {
        this.useEmailAddressAsUsername = value;
    }

    /**
     * This allows you to declaratively set a map containing values that will substitute role names from LDAP to Spark roles names in cases that they might be different.
     * For example, if you have a role specified in LDAP under "memberOf" with a DN of "Marketing Administrator", you might want to
     * map that to the role "ADMIN".  By default the prefix "ROLE_" will be pre-pended to this name. So to configure this, you would specify:
     *
     * <bean class="org.spark.loadtest.web.security.ActiveDirectoryUserDetailsContextMapper">
     *     <property name="roleMappings">
     *         <map>
     *             <entry key="Marketing_Administrator" value="CATALOG_ADMIN"/>
     *         </map>
     *     </property>
     * </bean>
     *
     * With this configuration, all roles returned by LDAP that have a DN of "Marketing Administrator" will be converted to "ADMIN"
     * @param roleNameSubstitutions
     */
    public void setRoleNameSubstitutions(Map<String, String[]> roleNameSubstitutions) {
        this.roleNameSubstitutions = roleNameSubstitutions;
    }

    /**
     * This should be used in conjunction with the roleNameSubstitutions property.
     * If this is set to true, this will add the mapped roles to the list of original granted authorities.  If set to false, this will replace the original granted
     * authorities with the mapped ones. Defaults to false.
     *
     * @param additiveRoleNameSubstitutions
     */
    public void setAdditiveRoleNameSubstitutions(boolean additiveRoleNameSubstitutions) {
        this.additiveRoleNameSubstitutions = additiveRoleNameSubstitutions;
    }
}
