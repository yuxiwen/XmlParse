package com.hcss.util.transction;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * @description 事务管理
 *
 * @author 余锡文(yuxw@zjhcsoft.com)
 *
 * @version TransactionManager.java v 1.0 2016年11月18日 下午2:24:31
 *
 */
public class TransactionManager {

    private DataSource dataSource;

    public TransactionManager(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    public final void start() throws SQLException {

        Connection connection = getConnection();

        connection.setAutoCommit(false);
    }

    public final void commit() throws SQLException {

        Connection connection = getConnection();

        connection.commit();
    }

    public final void rollback() {

        Connection connection = null;

        try {
            connection = getConnection();

            connection.rollback();

        } catch (SQLException e) {

            throw new RuntimeException("Couldn't rollback on connection[" + connection + "].", e);
        }
    }

    public final void close() {

        Connection connection = null;

        try {
            connection = getConnection();

            connection.setAutoCommit(true);

            connection.setReadOnly(false);

            connection.close();

            SingleThreadConnectionHolder.removeConnection(dataSource);

        } catch (SQLException e) {

            throw new RuntimeException("Couldn't close connection[" + connection + "].", e);
        }
    }

    private Connection getConnection() throws SQLException {

        return SingleThreadConnectionHolder.getConnection(dataSource);
    }

}
