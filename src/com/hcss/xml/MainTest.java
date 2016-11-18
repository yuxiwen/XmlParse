package com.hcss.xml;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.hcss.xml.biz.XmlParseBiz;
import com.hcss.xml.dao.DataSourceFactory;
import com.hcss.xml.service.XmlParseService;
import com.hcss.xml.service.impl.XmlParseServiceImpl;

/**
 * @description
 *
 * @author 余锡文(yuxw@zjhcsoft.com)
 *
 * @version MainTest.java v 1.0 2016年11月8日 上午9:16:20
 *
 */
public class MainTest {

    protected static final DataSource dataSource = DataSourceFactory.createDataSource();

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        XmlParseService xmlParseService = new XmlParseServiceImpl();

        File file = new File("D:\\workspace_05\\XmlParse\\src\\com\\hcss\\xml\\file\\test3.xml");

        Map<String, Object> map = xmlParseService.getDataByFile(file);

        for (String key : map.keySet()) {

            Object object = map.get(key);

            if (object instanceof String) {

                System.out.println(key + "-->" + object.toString());

            } else {

                System.out.print(key + "--> [");

                for (String value : (List<String>) object) {

                    System.out.print(value + ",");
                }

                System.out.println("]");
            }
        }


        // -----------------------华丽丽的分割线-------------------------------------

//        // 多行sql
////        String sql = "insert into A(a1,a2,a3) values(#ASTNO#,#ACODE_ABTSC#,#AESTR#)";
//
//        // 行转列
////        String sql = "insert into B(b1,b2,b3,b4,b5) values(#ASTNO0#,#ACODE0_ABTSC0#,#ACODE1_ABTSC1#,#AESTR0#,#AESTR1#)";
//
//        String sql = "update B set b4 = #ACODE0_ABTSC0#, b5 = #ACODE1_ABTSC1# where b1 = #ASTNO0#";
//
//
//        try {
//
////            map = SqlParseUtils.parseMap(map,"DFIE");
//
//            // 单行sql
//            sql = SqlParseUtils.singleParse(sql, map);
//
//            System.out.println(sql);
//
//            // 多行sql
////            List<String> sqlList = SqlParseUtils.multiParse(sql, map);
////
////            for (String sql1 : sqlList)  System.out.println(sql1);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }


        // -----------------------华丽丽的分割线-------------------------------------


        XmlParseBiz xmlParseBiz = new XmlParseBiz(dataSource);

        xmlParseBiz.execByFile(file);




    }

}
