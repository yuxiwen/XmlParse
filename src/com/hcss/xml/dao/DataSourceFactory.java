package com.hcss.xml.dao;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * @description
 *
 * @author 余锡文(yuxw@zjhcsoft.com)
 *
 * @version DataSourceFactory.java v 1.0 2016年11月18日 下午3:12:53
 *
 */
public class DataSourceFactory {

    private static final BasicDataSource dataSource = new BasicDataSource();

    static {

        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");

        dataSource.setUsername("hcss_xml");

        dataSource.setPassword("hcss_xml");

        dataSource.setUrl("jdbc:oracle:thin:@10.80.18.201:1521:fiona");
    }

    public static DataSource createDataSource() {
        return dataSource;
    }

}
