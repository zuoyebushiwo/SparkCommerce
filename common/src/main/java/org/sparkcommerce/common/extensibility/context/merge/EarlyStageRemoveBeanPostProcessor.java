/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.extensibility.context.merge;

import org.springframework.core.PriorityOrdered;

/**
 * Use this merge bean post processor for collection member removal tasks that should take place before the persistence layer is
 * initialized. This would include removing class transformers for load time weaving, and the like. See
 * {@link org.sparkcommerce.common.extensibility.context.merge.AbstractRemoveBeanPostProcessor} for usage information.
 *
 * @see org.sparkcommerce.common.extensibility.context.merge.AbstractRemoveBeanPostProcessor
 *
 *
 * @author Adasari
 */
public class EarlyStageRemoveBeanPostProcessor extends AbstractRemoveBeanPostProcessor implements PriorityOrdered {

    protected int order = Integer.MIN_VALUE;

    /**
     * This is the priority order for this post processor and will determine when this processor is run in relation
     * to other priority ordered processors (e.g. {@link org.springframework.context.annotation.CommonAnnotationBeanPostProcessor})
     * The default value if Integer.MIN_VALUE.
     */
    @Override
    public int getOrder() {
        return order;
    }

    /**
     * This is the priority order for this post processor and will determine when this processor is run in relation
     * to other priority ordered processors (e.g. {@link org.springframework.context.annotation.CommonAnnotationBeanPostProcessor})
     * The default value if Integer.MIN_VALUE.
     *
     * @param order the priority ordering
     */
    public void setOrder(int order) {
        this.order = order;
    }

}
