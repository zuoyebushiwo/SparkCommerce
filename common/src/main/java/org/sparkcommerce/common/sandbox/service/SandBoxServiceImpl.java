/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.sandbox.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sparkcommerce.common.sandbox.dao.SandBoxDao;
import org.sparkcommerce.common.sandbox.domain.SandBox;
import org.sparkcommerce.common.sandbox.domain.SandBoxType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

@Service(value = "blSandBoxService")
public class SandBoxServiceImpl implements SandBoxService {
    
    private static final Log LOG = LogFactory.getLog(SandBoxServiceImpl.class);

    @Resource(name = "blSandBoxDao")
    protected SandBoxDao sandBoxDao;

    @Override
    public SandBox retrieveSandBoxById(Long sandboxId) {
        return sandBoxDao.retrieve(sandboxId);
    }
    
    @Override
    public List<SandBox> retrieveAllSandBoxes() {
        return sandBoxDao.retrieveAllSandBoxes();
    }
    
    @Override
    public List<SandBox> retrieveSandBoxesByType(SandBoxType type) {
        return sandBoxDao.retrieveSandBoxesByType(type);
    }
    
    @Override
    public SandBox retrieveUserSandBoxForParent(Long authorId, Long parentSandBoxId) {
        return sandBoxDao.retrieveUserSandBoxForParent(authorId, parentSandBoxId);
    }
    
    @Override
    public List<SandBox> retrievePreviewSandBoxes(Long authorId) {
        List<SandBox> returnList = new ArrayList<SandBox>();
        List<SandBox> authorBoxes = sandBoxDao.retrieveSandBoxesForAuthor(authorId);
        List<SandBox> approvalBoxes = sandBoxDao.retrieveSandBoxesByType(SandBoxType.APPROVAL);
        List<SandBox> defaultBoxes = sandBoxDao.retrieveSandBoxesByType(SandBoxType.DEFAULT);

        List<SandBox> candidateBoxes = new ArrayList<SandBox>();
        candidateBoxes.addAll(approvalBoxes);
        candidateBoxes.addAll(defaultBoxes);
        
        returnList.addAll(authorBoxes);

        for (SandBox cb : candidateBoxes) {
            boolean match = false;
            for (SandBox authorBox : authorBoxes) {
                if (authorBox.getId().equals(cb.getId()) || 
                        (authorBox.getParentSandBox() != null && authorBox.getParentSandBox().getId().equals(cb.getId()))) {
                    match = true;
                }
            }
            if (!match) {
                returnList.add(cb);
            }
        }
        
        return returnList;
    }

    @Override
    public SandBox retrieveUserSandBox(Long authorId, Long overrideSandBoxId, String sandBoxName) {
        SandBox userSandbox;
        if (overrideSandBoxId != null) {
            userSandbox = retrieveSandBoxById(overrideSandBoxId);
        } else {
            userSandbox = retrieveSandBox(sandBoxName, SandBoxType.USER);
            if (userSandbox == null) {
                userSandbox = createSandBox(sandBoxName, SandBoxType.USER);
            }
        }

        return userSandbox;
    }
    
    @Override
    public Map<Long, String> retrieveAuthorNamesForSandBoxes(Set<Long> sandBoxIds) {
        return sandBoxDao.retrieveAuthorNamesForSandBoxes(sandBoxIds);
    }

    @Override
    public synchronized SandBox createSandBox(String sandBoxName, SandBoxType sandBoxType) {
        return sandBoxDao.createSandBox(sandBoxName, sandBoxType);
    }
    
    @Override
    public synchronized SandBox createUserSandBox(Long authorId, SandBox approvalSandBox) {
        return sandBoxDao.createUserSandBox(authorId, approvalSandBox);
    }
    
    @Override
    public synchronized SandBox createDefaultSandBox() {
        return sandBoxDao.createDefaultSandBox();
    }

    @Override
    public SandBox retrieveSandBox(String sandBoxName, SandBoxType sandBoxType) {
        return sandBoxDao.retrieveNamedSandBox(sandBoxType, sandBoxName);
    }

    @Override
    public List<SandBox> retrieveAllUserSandBoxes(Long authorId) {
        return sandBoxDao.retrieveAllUserSandBoxes(authorId);
    }
    
}
