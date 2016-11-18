package com.hcss.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.hcss.xml.dao.XmlParseDao;
import com.hcss.xml.model.XmlMapModel;

/**
 * @description
 *
 * @author 余锡文(yuxw@zjhcsoft.com)
 *
 * @version SqlParseUtils.java v 1.0 2016年11月10日 下午1:45:36
 *
 */
public class SqlParseUtils {

    /**
     * 多行解析sql成可执行sql语句
     *
     * @param sql
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<String> multiParse(String sql, Map<String, Object> map) throws Exception{

        List<String> sqlList = new ArrayList<String>();

        List<String> keyList = getKeys(sql);

        /*
         * keyList不为空时,
         * 取第一个key(非组合键)作为基数,其对应的value决定行数,
         * List对应多行;
         * String对应单行.
         */
        if (CollectionUtils.isEmpty(keyList)) return null;

        Object object = map.get(keyList.get(0));

        if (object instanceof List) {

            List<String> valueList = (List<String>) object;

            // 循环第一个key对应的List，得到多行sql语句
            for (int i = 0; i < valueList.size(); i++) {

                String sql0 = StringUtils.replace(sql, "#" + keyList.get(0) + "#", "'" + valueList.get(i) + "'");

                // 获取从第二个key开始对应的值
                for (int j = 1; j < keyList.size(); j++) {

                    StringBuffer value1 = new StringBuffer("'");

                    // 当前为组合键：key2_key3
                    if (keyList.get(j).contains("_")) {

                        String[] keyArr = keyList.get(j).split("_");

                        // 循环获取组合键的值
                        for (int k = 0; k < keyArr.length; k++) {

                            getValue(map,keyArr[k],value1,i);
                        }

                    } else { // 非组合键情况

                        getValue(map,keyList.get(j),value1,i);
                    }

                    value1.append("'");

                    sql0 = StringUtils.replace(sql0, "#" + keyList.get(j) + "#", value1.toString());
                }

                sqlList.add(sql0);
            }

        } else if (object instanceof String){

            sqlList.add(singleParse(sql, map));

        } else {

            throw new Exception("第一个键不能为组合键");

        }

        return sqlList;

    }

    /**
     * 根据key获取对应的value
     *
     * @param map
     * @param origenKey
     * @param value1
     * @param i
     */
    @SuppressWarnings("unchecked")
    private static void getValue(Map<String,Object> map,String origenKey,StringBuffer value1,Integer i) {

        Object object1 = map.get(origenKey);

        if (object1 instanceof List) {

            value1.append(((List<String>) object1).get(i));

        } else {

            value1.append(object1.toString());
        }
    }

    /**
     * 单行解析sql成可执行sql语句
     *
     * @param sql
     * @return
     */
    public static String singleParse(String sql, Map<String, Object> map) throws Exception{

        List<String> keyList = getKeys(sql);

        // keyList不为空时,循环获取key对应的值
        if (CollectionUtils.isEmpty(keyList)) return null;

        for (int i = 0; i < keyList.size(); i++) {

            StringBuffer value = new StringBuffer("'");

            // 当前为组合键：key1_key2
            if (keyList.get(i).contains("_")) {

                String[] keyArr = keyList.get(i).split("_");

                // 循环获取组合键的值
                for (int j = 0; j < keyArr.length; j++) {

                    getValue(map, keyArr[j], value);
                }

            } else { // 非组合键情况：key3

                getValue(map, keyList.get(i), value);
            }

            value.append("'");

            sql = StringUtils.replace(sql, "#" + keyList.get(i) + "#", value.toString());

        }

        return sql;

    }

    /**
     * 根据key获取对应的value
     *
     * @param map
     * @param origenKey
     * @param value
     * @throws NumberFormatException
     */
    @SuppressWarnings("unchecked")
    private static void getValue(Map<String, Object> map, String origenKey, StringBuffer value) throws NumberFormatException {

        // 确定脚标位置
        String[] keys = origenKey.split("[\\d]");

        String key = origenKey.substring(0,keys[0].length());

        Integer index = StringUtils.isBlank(origenKey.substring(keys[0].length()))?null:Integer.valueOf(origenKey.substring(keys[0].length()));

        // 获取当前键的值
        Object object = map.get(key);

        /*
         * 根据脚标是否存在判断value的类型:
         * 不存在value为String;
         * 存在value为List.
         */
        if (index==null) {

            value.append(object.toString());

        } else {

            value.append(((List<String>)object).get(index));
        }

    }

    /**
     * 截取sql语句中占位符“#”之间的key
     *
     * @param sql
     * @return
     */
    public static List<String> getKeys(String sql) {

        List<String> keyList = new ArrayList<String>();

        String regex = "#([[A-Z|\\d|\\_]+\\.]+)#";

        Pattern pattern = Pattern.compile(regex);

        Matcher m = pattern.matcher(sql);

        while (m.find()) keyList.add(m.group(1));

        return keyList;
    }

    /**
     * 根据映射关系表，去掉key的前缀
     *
     * @param xmlParseDao
     * @param map
     * @param xmlType
     * @return
     * @throws Exception
     */
    public static Map<String,Object> parseMap(XmlParseDao xmlParseDao, Map<String,Object> map, String xmlType) throws Exception{

        Map<String,Object> newMap = new HashMap<String, Object>();

        // 根据xml_type查询对应关系表
        List<XmlMapModel> list = xmlParseDao.select("SELECT ID,XML_TYPE,ORIGEN_KEY,NEW_KEY FROM HCSS_XML_MAP WHERE XML_TYPE = '"+ xmlType +"'", XmlMapModel.class);

        for (XmlMapModel xmlMap : list) {

            newMap.put(xmlMap.getNew_key(), map.get(xmlMap.getOrigen_key()));

            map.remove(xmlMap.getOrigen_key());
        }

        for (String key : map.keySet()) {

            String newKey = key.substring(key.lastIndexOf(".")+1);

            newMap.put(newKey, map.get(key));
        }

        return newMap;

    }


}
