/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.site.domain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sparkcommerce.common.admin.domain.AdminMainEntity;
import org.sparkcommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import org.sparkcommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import org.sparkcommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import org.sparkcommerce.common.presentation.AdminPresentation;
import org.sparkcommerce.common.presentation.AdminPresentationClass;
import org.sparkcommerce.common.presentation.AdminPresentationCollection;
import org.sparkcommerce.common.presentation.client.AddMethodType;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author Joel Dasari
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="SC_CATALOG")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITEMARKER)
})
@AdminPresentationClass(friendlyName = "CatalogImpl")
public class CatalogImpl implements Catalog, AdminMainEntity {

    private static final Log LOG = LogFactory.getLog(CatalogImpl.class);

    @Id
    @GeneratedValue(generator= "CatalogId")
    @GenericGenerator(
        name="CatalogId",
        strategy="org.sparkcommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="CatalogImpl"),
            @Parameter(name="entity_name", value="org.sparkcommerce.common.site.domain.CatalogImpl")
        }
    )
    @Column(name = "CATALOG_ID")
    protected Long id;

    @Column(name = "NAME")
    @AdminPresentation(friendlyName = "Catalog_Name", gridOrder = 1, order=1, prominent = true)
    protected String name;
    
    @ManyToMany(targetEntity = SiteImpl.class)
    @JoinTable(name = "SC_SITE_CATALOG", joinColumns = @JoinColumn(name = "CATALOG_ID"), inverseJoinColumns = @JoinColumn(name = "SITE_ID"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
    @BatchSize(size = 50)
    @AdminPresentationCollection(addType = AddMethodType.LOOKUP, friendlyName = "sitesTitle", manyToField = "catalogs")
    protected List<Site> sites = new ArrayList<Site>();
    
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<Site> getSites() {
        return sites;
    }

    @Override
    public void setSites(List<Site> sites) {
        this.sites = sites;
    }

    public void checkCloneable(Catalog catalog) throws CloneNotSupportedException, SecurityException, NoSuchMethodException {
        Method cloneMethod = catalog.getClass().getMethod("clone", new Class[]{});
        if (cloneMethod.getDeclaringClass().getName().startsWith("org.sparkcommerce") && !catalog.getClass().getName().startsWith("org.sparkcommerce")) {
            //subclass is not implementing the clone method
            throw new CloneNotSupportedException("Custom extensions and implementations should implement clone.");
        }
    }

    @Override
    public Catalog clone() {
        Catalog clone;
        try {
            clone = (Catalog) Class.forName(this.getClass().getName()).newInstance();
            try {
                checkCloneable(clone);
            } catch (CloneNotSupportedException e) {
                LOG.warn("Clone implementation missing in inheritance hierarchy outside of Spark: " + clone.getClass().getName(), e);
            }
            clone.setId(id);
            clone.setName(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return clone;
    }

    @Override
    public String getMainEntityName() {
        return getName();
    }

}
