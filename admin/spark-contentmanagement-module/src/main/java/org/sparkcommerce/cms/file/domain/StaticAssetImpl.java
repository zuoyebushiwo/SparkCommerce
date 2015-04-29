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
package org.sparkcommerce.cms.file.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

import org.sparkcommerce.cms.field.type.StorageType;
import org.sparkcommerce.common.admin.domain.AdminMainEntity;
import org.sparkcommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import org.sparkcommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import org.sparkcommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import org.sparkcommerce.common.locale.domain.LocaleImpl;
import org.sparkcommerce.common.presentation.AdminPresentation;
import org.sparkcommerce.common.presentation.AdminPresentationClass;
import org.sparkcommerce.common.presentation.AdminPresentationMap;
import org.sparkcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.sparkcommerce.common.presentation.RequiredOverride;
import org.sparkcommerce.common.presentation.client.SupportedFieldType;
import org.sparkcommerce.common.presentation.client.VisibilityEnum;
import org.sparkcommerce.common.presentation.override.AdminPresentationOverride;
import org.sparkcommerce.common.presentation.override.AdminPresentationOverrides;
import org.sparkcommerce.openadmin.audit.AdminAuditable;
import org.sparkcommerce.openadmin.audit.AdminAuditableListener;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;

/**
 * Created by bpolster.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(value = { AdminAuditableListener.class })
@Table(name = "SC_STATIC_ASSET")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blCMSElements")
@AdminPresentationOverrides(
        {
            @AdminPresentationOverride(name="auditable.createdBy.id", value=@AdminPresentation(readOnly = true, visibility = VisibilityEnum.HIDDEN_ALL)),
            @AdminPresentationOverride(name="auditable.updatedBy.id", value=@AdminPresentation(readOnly = true, visibility = VisibilityEnum.HIDDEN_ALL)),
            @AdminPresentationOverride(name="auditable.createdBy.name", value=@AdminPresentation(readOnly = true, visibility = VisibilityEnum.HIDDEN_ALL)),
            @AdminPresentationOverride(name="auditable.updatedBy.name", value=@AdminPresentation(readOnly = true, visibility = VisibilityEnum.HIDDEN_ALL)),
            @AdminPresentationOverride(name="auditable.dateCreated", value=@AdminPresentation(readOnly = true, visibility = VisibilityEnum.HIDDEN_ALL)),
            @AdminPresentationOverride(name="auditable.dateUpdated", value=@AdminPresentation(readOnly = true, visibility = VisibilityEnum.HIDDEN_ALL)),
            @AdminPresentationOverride(name="sandbox", value=@AdminPresentation(excluded = true))
        }
)
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE)
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_CATALOG),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITE)
})
public class StaticAssetImpl implements StaticAsset, AdminMainEntity {

    private static final long serialVersionUID = 6990685254640110350L;

    @Id
    @GeneratedValue(generator = "StaticAssetId")
    @GenericGenerator(
        name="StaticAssetId",
        strategy="org.sparkcommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="StaticAssetImpl"),
            @Parameter(name="entity_name", value="org.sparkcommerce.cms.file.domain.StaticAssetImpl")
        }
    )
    @Column(name = "STATIC_ASSET_ID")
    protected Long id;

    @Embedded
    @AdminPresentation(excluded = true)
    protected AdminAuditable auditable = new AdminAuditable();

    @Column(name = "NAME", nullable = false)
    @AdminPresentation(friendlyName = "StaticAssetImpl_Item_Name",
            order = Presentation.FieldOrder.NAME,
            requiredOverride = RequiredOverride.NOT_REQUIRED,
            gridOrder = Presentation.FieldOrder.NAME,
            prominent = true)
    protected String name;

    @Column(name ="FULL_URL", nullable = false)
    @AdminPresentation(friendlyName = "StaticAssetImpl_Full_URL",
            order = Presentation.FieldOrder.URL,
            gridOrder = Presentation.FieldOrder.URL,
            requiredOverride = RequiredOverride.REQUIRED,
            fieldType = SupportedFieldType.ASSET_URL,
            prominent = true)
    @Index(name="ASST_FULL_URL_INDX", columnNames={"FULL_URL"})
    protected String fullUrl;

    @Column(name = "TITLE", nullable = true)
    @AdminPresentation(friendlyName = "StaticAssetImpl_Title",
            order = Presentation.FieldOrder.TITLE,
            translatable = true)
    protected String title;

    @Column(name = "ALT_TEXT", nullable = true)
    @AdminPresentation(friendlyName = "StaticAssetImpl_Alt_Text",
            order = Presentation.FieldOrder.ALT_TEXT,
            translatable = true)
    protected String altText;

    @Column(name = "MIME_TYPE")
    @AdminPresentation(friendlyName = "StaticAssetImpl_Mime_Type",
            order = Presentation.FieldOrder.MIME_TYPE,
            tab = Presentation.Tab.Name.File_Details, tabOrder = Presentation.Tab.Order.File_Details,
            readOnly = true)
    protected String mimeType;

    @Column(name = "FILE_SIZE")
    @AdminPresentation(friendlyName = "StaticAssetImpl_File_Size_Bytes",
            order = Presentation.FieldOrder.FILE_SIZE,
            tab = Presentation.Tab.Name.File_Details, tabOrder = Presentation.Tab.Order.File_Details,
            readOnly = true)
    protected Long fileSize;

    @Column(name = "FILE_EXTENSION")
    @AdminPresentation(friendlyName = "StaticAssetImpl_File_Extension",
            order = Presentation.FieldOrder.FILE_EXTENSION,
            tab = Presentation.Tab.Name.File_Details, tabOrder = Presentation.Tab.Order.File_Details,
            readOnly = true)
    protected String fileExtension;

    @ManyToMany(targetEntity = StaticAssetDescriptionImpl.class, cascade = CascadeType.ALL)
    @JoinTable(name = "SC_ASSET_DESC_MAP", joinColumns = @JoinColumn(name = "STATIC_ASSET_ID"),
            inverseJoinColumns = @JoinColumn(name = "STATIC_ASSET_DESC_ID"))
    @MapKeyColumn(name = "MAP_KEY")
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blCMSElements")
    @BatchSize(size = 20)
    @AdminPresentationMap(
        excluded = true,
            tab = Presentation.Tab.Name.Advanced, tabOrder = Presentation.Tab.Order.Advanced,
        friendlyName = "assetDescriptionTitle",
        keyPropertyFriendlyName = "SkuImpl_Sku_Media_Key",
        deleteEntityUponRemove = true,
        mapKeyOptionEntityClass = LocaleImpl.class,
        mapKeyOptionEntityDisplayField = "friendlyName",
        mapKeyOptionEntityValueField = "localeCode"
)
    protected Map<String,StaticAssetDescription> contentMessageValues = new HashMap<String,StaticAssetDescription>();

    @Column(name = "STORAGE_TYPE")
    @AdminPresentation(excluded = true)
    protected String storageType;

    @Override
    public String getFullUrl() {
        return fullUrl;
    }

    @Override
    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getAltText() {
        return altText;
    }

    @Override
    public void setAltText(String altText) {
        this.altText = altText;
    }

    @Override
    public Long getFileSize() {
        return fileSize;
    }

    @Override
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public Map<String, StaticAssetDescription> getContentMessageValues() {
        return contentMessageValues;
    }

    @Override
    public void setContentMessageValues(Map<String, StaticAssetDescription> contentMessageValues) {
        this.contentMessageValues = contentMessageValues;
    }

    @Override
    public String getMimeType() {
        return mimeType;
    }

    @Override
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public String getFileExtension() {
        return fileExtension;
    }

    @Override
    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @Override
    public AdminAuditable getAuditable() {
        return auditable;
    }

    @Override
    public void setAuditable(AdminAuditable auditable) {
        this.auditable = auditable;
    }

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
    public StorageType getStorageType() {
        StorageType st = StorageType.getInstance(storageType);
        if (st == null) {
            return StorageType.DATABASE;
        } else {
            return st;
        }
    }

    @Override
    public void setStorageType(StorageType storageType) {
        this.storageType = storageType.getType();
    }

    public static class Presentation {

        public static class Tab {

            public static class Name {

                public static final String File_Details = "StaticAssetImpl_FileDetails_Tab";
                public static final String Advanced = "StaticAssetImpl_Advanced_Tab";
            }

            public static class Order {

                public static final int File_Details = 2000;
                public static final int Advanced = 3000;
            }
        }

        public static class FieldOrder {

            // General Fields
            public static final int NAME = 1000;
            public static final int URL = 2000;
            public static final int TITLE = 3000;
            public static final int ALT_TEXT = 4000;

            public static final int MIME_TYPE = 5000;
            public static final int FILE_EXTENSION = 6000;
            public static final int FILE_SIZE = 7000;
            
            // Used by subclasses to know where the last field is.
            public static final int LAST = 7000;

        }
    }

    @Override
    public String getMainEntityName() {
        return getName();
    }
}
