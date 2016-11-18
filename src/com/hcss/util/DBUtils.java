package com.hcss.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @description
 *
 * @author 余锡文(yuxw@zjhcsoft.com)
 *
 * @version DBUtils.java v 1.0 2016年11月17日 下午1:08:18
 *
 */
public class DBUtils {

    /**
     * 根据传递的数据库连接，执行无参数保存操作 不会自动提交，也不会释放连接数据库Connection资源。
     *
     * @param sql 要执行的数据操作语句。
     * @throws SQLException 执行数据操作语句失败时，抛出异常。
     * @return 返回执行sql影响的行数
     */
    public static Integer execSQL(Connection conn, String sql) throws SQLException {

        // 入口参数合法性校验
        if (sql == null)   throw new SQLException("要执行的SQL语句为空");

        if (conn == null || conn.isClosed())  throw new SQLException("连接为空或已关闭");

        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement(sql);

            return ps.executeUpdate();

        } catch (SQLException ex) {

            throw ex;

        } finally {

            closeStatement(ps);
        }

    }

    /**
     * 根据传入的SQL执行查询，并返回一个Collection的记录结果结合，
     * 其中每条记录以HashMap格式存放，HashMap的key就是该值对应字段的顺序号（从1开始）
     *
     * @param con
     * @param sql
     * @param clazz
     * @return
     * @throws Exception
     */
    public static<T> List<T> querySQL(Connection conn, String sql, Class<T> clazz ) throws Exception {

        // 入口参数合法性校验
        if (sql == null)  throw new SQLException("要查询的SQL语句为空");

        if (conn == null || conn.isClosed() ) throw new SQLException("连接为空");

        PreparedStatement pstm = null;

        ResultSet res = null;

        try {
            pstm = conn.prepareStatement(sql);

            res = pstm.executeQuery();

            // 将结果Set转换成对象集合返回
            return ResultSetToList.populate(res, clazz);

        } catch (SQLException ex) {

            throw ex;

        }catch (Exception e) {

            throw e;

        } finally {

            closeResStat(res, pstm);
        }

    }

    /**
     * 释放ResultSet和Statement，自动忽略异常
     *
     * @param rs
     * @param ps
     */
    public static void closeResStat(ResultSet rs, Statement ps) {

        closeResult(rs);

        closeStatement(ps);
    }

    /**
     * 释放ResultSet，自动忽略异常
     *
     * @param rs
     * @throws SQLException
     */
    public static void closeResult(ResultSet rs) {

        if (rs != null) {

            try {
                rs.close();

            } catch (Exception e) {

                e.printStackTrace();
            }

            rs = null;
        }
    }


    /**
     * 释放Statement，自动忽略异常
     *
     * @param statement
     * @throws SQLException
     */
    public static void closeStatement(Statement statement) {

        if (statement != null) {

            try {

                statement.close();

            } catch (Exception e) {

                e.printStackTrace();
            }

            statement = null;
        }
    }


    /**
     * 释放Connection，自动忽略异常
     *
     * @param con
     * @throws SQLException
     */
    public static void closeConnection(Connection con) {

        if (con != null) {

            try {

                rollback(con);

                con.close();

            } catch (Exception e) {

                e.printStackTrace();
            }

            con = null;
        }
    }


    /**
     * 数据库连接事务回滚，无需考虑异常
     *
     * @param con
     */
    public static void rollback(Connection con) {

        if (con != null) {

            try {

                con.rollback();

            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }


    /**
     * 数据库事务提交
     *
     * @param con
     * @exception SQLException
     */
    public static void commit(Connection con) throws SQLException {

        if (con != null) con.commit();
    }

}
