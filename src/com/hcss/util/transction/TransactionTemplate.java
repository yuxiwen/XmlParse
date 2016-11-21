package com.hcss.util.transction;

import javax.sql.DataSource;

/**
 * @description 事物处理模板抽象类
 *
 * @author 余锡文(yuxw@zjhcsoft.com)
 *
 * @version TransactionTemplate.java v 1.0 2016年11月21日 上午9:06:19
 *
 */
public abstract class TransactionTemplate {

    private TransactionManager transactionManager;

    protected TransactionTemplate(DataSource dataSource)
    {
        transactionManager = new TransactionManager(dataSource);
    }

    public void doJobInTransaction()
    {
        try
        {
            transactionManager.start();
            doJob();
            transactionManager.commit();
        } catch (Exception e)
        {
            transactionManager.rollback();
        } finally
        {
            transactionManager.close();
        }
    }

    protected abstract void doJob() throws Exception;

}
