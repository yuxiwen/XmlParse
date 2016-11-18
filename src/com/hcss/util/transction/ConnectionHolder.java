package com.hcss.util.transction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

/**
 * @description 存放全局Connection的，达到共享目的
 *
 * @author 余锡文(yuxw@zjhcsoft.com)
 *
 * @version ConnectionHolder.java v 1.0 2016年11月18日 下午2:30:02
 *
 */
public class ConnectionHolder {

    private Map<DataSource, Connection> connectionMap = new HashMap<DataSource, Connection>();

    public Connection getConnection(DataSource dataSource) throws SQLException {

        Connection connection = connectionMap.get(dataSource);

        if (connection == null || connection.isClosed()) {

            connection = dataSource.getConnection();

            connectionMap.put(dataSource, connection);
        }

        return connection;
    }

    public void removeConnection(DataSource dataSource) {

        connectionMap.remove(dataSource);
    }

}
