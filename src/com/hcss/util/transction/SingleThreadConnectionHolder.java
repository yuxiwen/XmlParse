package com.hcss.util.transction;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * @description 获取线程安全的ConnectionHolder
 *
 * @author 余锡文(yuxw@zjhcsoft.com)
 *
 * @version SingleThreadConnectionHolder.java v 1.0 2016年11月18日 下午2:28:40
 *
 */
public class SingleThreadConnectionHolder {

    private static ThreadLocal<ConnectionHolder> localConnectionHolder = new ThreadLocal<ConnectionHolder>();

    public static Connection getConnection(DataSource dataSource) throws SQLException {

        return getConnectionHolder().getConnection(dataSource);
    }

    public static void removeConnection(DataSource dataSource) {

        getConnectionHolder().removeConnection(dataSource);
    }

    private static ConnectionHolder getConnectionHolder() {

        ConnectionHolder connectionHolder = localConnectionHolder.get();

        if (connectionHolder == null) {

            connectionHolder = new ConnectionHolder();

            localConnectionHolder.set(connectionHolder);
        }
        return connectionHolder;
    }

}
