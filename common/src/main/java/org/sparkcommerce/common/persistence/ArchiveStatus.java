/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.persistence;

import org.sparkcommerce.common.presentation.AdminPresentation;
import org.sparkcommerce.common.presentation.client.VisibilityEnum;
import org.sparkcommerce.common.sandbox.SandBoxNonProductionSkip;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author J Dasari
 */
@Embeddable
public class ArchiveStatus implements Serializable, SandBoxNonProductionSkip {

    @Column(name = "ARCHIVED")
    @AdminPresentation(friendlyName = "archived", visibility = VisibilityEnum.HIDDEN_ALL, group = "ArchiveStaobjus")
    protected Character archived = 'N';

    public Character getArchived() {
        return archived;
    }

    public void setArchived(Character archived) {
        this.archived = archived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;

        ArchiveStatus that = (ArchiveStatus) o;

        if (archived != null ? !archived.equals(that.archived) : that.archived != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return archived != null ? archived.hashCode() : 0;
    }
}
