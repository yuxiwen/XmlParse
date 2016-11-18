package com.hcss.xml.dao.base;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import com.hcss.util.DBUtils;
import com.hcss.util.transction.SingleThreadConnectionHolder;

/**
 * @description
 *
 * @author 余锡文(yuxw@zjhcsoft.com)
 *
 * @version BaseDao.java v 1.0 2016年11月17日 下午1:07:38
 *
 */
public class BaseDao {

    private DataSource dataSource;

    public BaseDao(DataSource dataSource) {

        this.dataSource = dataSource;

    }

    /**
     * 公共基础执行sql语句方法
     *
     * @param sql
     * @return
     * @throws Exception
     */
    public Integer execute(String sql) throws Exception{

        Connection conn = SingleThreadConnectionHolder.getConnection(dataSource);

        return DBUtils.execSQL(conn, sql);

    }

    /**
     * 公共基础查询方法
     *
     * @param sql
     * @param claz
     * @return
     * @throws Exception
     */
    public<T> List<T> select(String sql, Class<T> claz) throws Exception{

        Connection conn = SingleThreadConnectionHolder.getConnection(dataSource);

        return DBUtils.querySQL(conn, sql, claz);

    }


}
