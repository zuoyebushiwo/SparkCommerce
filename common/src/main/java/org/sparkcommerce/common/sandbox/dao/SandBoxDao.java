/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.sandbox.dao;

import org.sparkcommerce.common.sandbox.domain.SandBox;
import org.sparkcommerce.common.sandbox.domain.SandBoxType;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SandBoxDao {

    public SandBox retrieve(Long id);
    
    public List<SandBox> retrieveAllSandBoxes();

    public List<SandBox> retrieveSandBoxesByType(SandBoxType sandboxType);

    public List<SandBox> retrieveSandBoxesForAuthor(Long authorId);

    public SandBox retrieveUserSandBoxForParent(Long authorId, Long parentSandBoxId);

    public SandBox retrieveNamedSandBox(SandBoxType sandboxType, String sandboxName);

    public Map<Long, String> retrieveAuthorNamesForSandBoxes(Set<Long> sandBoxIds);

    public SandBox persist(SandBox entity);

    public SandBox createSandBox(String sandBoxName, SandBoxType sandBoxType);

    public SandBox createUserSandBox(Long authorId, SandBox approvalSandBox);

    public SandBox createDefaultSandBox();

    /**
     * Reads all SandBoxes that are of type {@link SandBoxType.USER} and belong to the given
     * user.
     * 
     * @param authorId
     * @return a list of SandBox belonging to the user
     */
    public List<SandBox> retrieveAllUserSandBoxes(Long authorId);

}
