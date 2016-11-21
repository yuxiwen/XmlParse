package com.hcss.xml;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

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


        xmlParseService.execByFile(file);




    }

}
