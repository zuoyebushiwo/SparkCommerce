/*
 * #%L
 * SparkCommerce Common Libraries
 * %%
 * Copyright (C) 2015  Spark Commerce
 * %%
 */
package org.sparkcommerce.common.util;

/**
 * @author Joel Dasari
 */
public interface StreamCapableTransactionalOperation extends TransactionalOperation {

    void pagedExecute(Object[] param) throws Throwable;

    Object[] retrievePage(int startPos, int pageSize);

    Long retrieveTotalCount();

    void executeAfterCommit(Object[] param);

    boolean shouldRetryOnTransactionLockAcquisitionFailure();

    int retryMaxCountOverrideForLockAcquisitionFailure();

}
