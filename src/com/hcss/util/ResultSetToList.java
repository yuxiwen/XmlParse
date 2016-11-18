package com.hcss.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description
 *
 * @author 余锡文(yuxw@zjhcsoft.com)
 *
 * @version ResultSetToList.java v 1.0 2016年11月17日 下午1:18:47
 *
 */
public class ResultSetToList {

    /**
     * 将结果Set转换成对象集合，其中对象的属性必须都是字符串
     *
     * @param rs
     * @param clazz
     * @return
     * @throws Exception
     */
    public static<T> List<T> populate(ResultSet rs, Class<T> clazz) throws Exception {

        ResultSetMetaData metaData = rs.getMetaData();

        int colCount = metaData.getColumnCount();

        List<T> ret = new ArrayList<T>();

        Field[] fields = clazz.getDeclaredFields();

        while (rs.next()) {

            T newInstance = clazz.newInstance();

            for (int i = 1; i <= colCount; i++) {
                try {

                    Object value = rs.getObject(i);

                    if(value == null){

                        value = "";

                    }else if (value.toString().indexOf("oracle.sql.CLOB@") != -1) {

                        Clob a = rs.getClob(i);

                        value = clobToString(a);
                    }

                    for (int j = 0; j < fields.length; j++) {

                        Field f = fields[j];

                        if (f.getName().equalsIgnoreCase(metaData.getColumnName(i))) {

                            Method method = clazz.getDeclaredMethod("set"+ upInitial(f.getName()), String.class);

                            method.invoke(newInstance, value.toString());
                        }
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
            ret.add(newInstance);
        }
        return ret;
    }

    /**
     * 将字符串第一个字符大写
     *
     * @param str
     * @return
     */
    public static String upInitial(String str) {

        char[] chars = str.toCharArray();

        chars[0] = Character.toUpperCase(chars[0]);

        return new String(chars);
    }

    /**
     * 将Clob类型数据转换成String
     *
     * @param clob
     * @return
     * @throws SQLException
     */
    public static String clobToString(Clob clob) throws SQLException {

        if (clob != null) {

            long len = clob.length();

            return clob.getSubString(1L, (int) len);

        } else {

            return null;
        }
    }

}
