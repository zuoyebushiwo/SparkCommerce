/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.structure.dto;

import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Scoped as a prototype bean via spark-cms-applicationContext-entity. This bean is used to wrap an {@link StructuredContentImpl}
 * so that modifications and additional properties can be used without worrying about Hibernate's persistence.
 * 
 * @author jdasari
 *
 * @see {@link StructuredContentServiceImpl#buildStructuredContentDTO};
 * @see {@link StructuredContentServiceImpl#buildFieldValues};
 */
public class StructuredContentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long id;
    protected String contentName;
    protected String contentType;
    protected String localeCode;
    protected Integer priority;
    protected Map values = new HashMap<String,String>();
    protected String ruleExpression;
    protected List<ItemCriteriaDTO> itemCriteriaDTOList;

    /**
     * Attempts to obtain the given property value from the dynamic property map first, and then an actual bean property
     * via a getter
     * 
     * @param propertyName
     * @return
     */
    public Object getPropertyValue(String propertyName) {
        try {
            return getValues().containsKey(propertyName) ? getValues().get(propertyName) : BeanUtils.getProperty(this, propertyName);
        } catch (Exception e) {
            return null;
        }
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        values.put("contentName", contentName);
        this.contentName = contentName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        values.put("contentType", contentType);
        this.contentType = contentType;
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        values.put("localeCode", localeCode);
        this.localeCode = localeCode;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        values.put("priority", priority);
        this.priority = priority;
    }

    public Map getValues() {
        return values;
    }

    public void setValues(Map values) {
        this.values = values;
    }

    public String getRuleExpression() {
        return ruleExpression;
    }

    public void setRuleExpression(String ruleExpression) {
        this.ruleExpression = ruleExpression;
    }

    public List<ItemCriteriaDTO> getItemCriteriaDTOList() {
        return itemCriteriaDTOList;
    }

    public void setItemCriteriaDTOList(List<ItemCriteriaDTO> itemCriteriaDTOList) {
        this.itemCriteriaDTOList = itemCriteriaDTOList;
    }
}
