/*
 * #%L
 * SparkCommerce CMS Module
 * %%
 * Copyright (C) 2009 - 2013 Spark Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.sparkcommerce.cms.page.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.sparkcommerce.cms.field.domain.FieldGroup;
import org.sparkcommerce.cms.field.domain.FieldGroupImpl;
import org.sparkcommerce.common.admin.domain.AdminMainEntity;
import org.sparkcommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import org.sparkcommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import org.sparkcommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import org.sparkcommerce.common.locale.domain.Locale;
import org.sparkcommerce.common.locale.domain.LocaleImpl;
import org.sparkcommerce.common.presentation.AdminPresentation;
import org.sparkcommerce.common.presentation.AdminPresentationClass;
import org.sparkcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.sparkcommerce.common.presentation.client.VisibilityEnum;
import org.sparkcommerce.common.extensibility.jpa.clone.ClonePolicyCollection;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Created by bpolster.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "SC_PAGE_TMPLT")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blCMSElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "PageTemplateImpl_basePageTemplate")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITE)
})
public class PageTemplateImpl implements PageTemplate, AdminMainEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "PageTemplateId")
    @GenericGenerator(
        name="PageTemplateId",
        strategy="org.sparkcommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="PageTemplateImpl"),
            @Parameter(name="entity_name", value="org.sparkcommerce.cms.page.domain.PageTemplateImpl")
        }
    )
    @Column(name = "PAGE_TMPLT_ID")
    @AdminPresentation(friendlyName = "PageTemplateImpl_Template_Id", 
        visibility = VisibilityEnum.HIDDEN_ALL, 
        readOnly = true)
    protected Long id;

    @Column (name = "TMPLT_NAME")
    @AdminPresentation(friendlyName = "PageTemplateImpl_Template_Name", 
        prominent = true, gridOrder = 1)
    protected String templateName;

    @Column (name = "TMPLT_DESCR")
    protected String templateDescription;

    @Column (name = "TMPLT_PATH")
    @AdminPresentation(friendlyName = "PageTemplateImpl_Template_Path", 
        visibility = VisibilityEnum.HIDDEN_ALL, 
        readOnly = true)
    protected String templatePath;

    @ManyToOne(targetEntity = LocaleImpl.class)
    @JoinColumn(name = "LOCALE_CODE")
    @AdminPresentation(excluded = true)
    protected Locale locale;

    @ManyToMany(targetEntity = FieldGroupImpl.class, cascade = {CascadeType.ALL})
    @JoinTable(name = "SC_PGTMPLT_FLDGRP_XREF", joinColumns = @JoinColumn(name = "PAGE_TMPLT_ID",
            referencedColumnName = "PAGE_TMPLT_ID"),
            inverseJoinColumns = @JoinColumn(name = "FLD_GROUP_ID", referencedColumnName = "FLD_GROUP_ID"))
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blCMSElements")
    @OrderColumn(name = "GROUP_ORDER")
    @BatchSize(size = 20)
    @ClonePolicyCollection(deepClone = false)
    protected List<FieldGroup> fieldGroups = new ArrayList<FieldGroup>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getTemplateName() {
        return templateName;
    }

    @Override
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    @Override
    public String getTemplateDescription() {
        return templateDescription;
    }

    @Override
    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }

    @Override
    public String getTemplatePath() {
        return templatePath;
    }

    @Override
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public List<FieldGroup> getFieldGroups() {
        return fieldGroups;
    }

    @Override
    public void setFieldGroups(List<FieldGroup> fieldGroups) {
        this.fieldGroups = fieldGroups;
    }

    @Override
    public String getMainEntityName() {
        return getTemplateName();
    }
}

