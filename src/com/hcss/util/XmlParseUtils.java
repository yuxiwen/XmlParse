package com.hcss.util;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

/**
 * @description
 *
 * @author 余锡文(yuxw@zjhcsoft.com)
 *
 * @version XmlParseUtils.java v 1.0 2016年11月8日 上午9:47:27
 *
 */
public class XmlParseUtils {

    private static Log logger = LogFactory.getLog(XmlParseUtils.class);

    /**
     * 根据InputSource解析XML,返回结果Map
     *
     * @param inputStream
     * @return
     */
    public static Map<String, Object> parse(InputSource inputSource) {

        Map<String, Object> map = new LinkedHashMap<String, Object>();

        try {

            SAXReader reader = new SAXReader();

            Document document = reader.read(inputSource);

            Element root = document.getRootElement();

            getElement(map, root, root.getName());

        } catch (Exception e) {

            logger.error("根据InputSource解析XML异常",e);

        }

        return map;
    }

    /**
     * 根据InputStream解析XML,返回结果Map
     *
     * @param inputStream
     * @return
     */
    public static Map<String, Object> parse(InputStream inputStream) {

        Map<String, Object> map = new LinkedHashMap<String, Object>();

        try {

            SAXReader reader = new SAXReader();

            Document document = reader.read(inputStream);

            Element root = document.getRootElement();

            getElement(map, root, root.getName());

        } catch (Exception e) {

            logger.error("根据InputStream解析XML异常",e);

        }

        return map;
    }

    /**
     * 根据File解析XML,返回结果Map
     *
     * @param file
     * @return
     */
    public static Map<String, Object> parse(File file) {

        Map<String, Object> map = new LinkedHashMap<String, Object>();

        try {

            SAXReader reader = new SAXReader();

            Document document = reader.read(file);

            Element root = document.getRootElement();

            getElement(map, root, root.getName());

        } catch (Exception e) {

            logger.error("根据File解析XML异常",e);

        }

        return map;
    }

    /**
     * 递归获取节点的值
     *
     * @param map
     * @param element
     * @param key
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private static Map<String, Object> getElement(Map<String, Object> map, Element element, String key) throws Exception {

        List<Element> list = element.elements();

        for (int i = 0; i < list.size(); i++) {

            List<Element> elist = list.get(i).elements();

            if (elist.size() == 0) {// 当前节点下不存在子节点

                String key0 = key + "." + list.get(i).getName();

                /**
                 * map中已存在相同的key,需要将相同的key-value变成List存到Map中;不存在直接保存key-value
                 */
                if (map.get(key0) != null) {

                    Object preValue = map.get(key0);

                    if (preValue instanceof String) { // 第一次遇到相同key

                        List<String> mapList = new ArrayList<String>();

                        map.remove(key0);

                        mapList.add(preValue.toString());

                        mapList.add(list.get(i).getText());

                        map.put(key0, mapList);

                    } else { // 第n>1次遇到相同key

                        List<String> preMapList = (List<String>) preValue;

                        preMapList.add(list.get(i).getText());

                    }

                } else {

                    map.put(key0, list.get(i).getText());

                }

            } else {// 当前节点下还有子节点

                String key1 = key + "." + list.get(i).getName();

                getElement(map, list.get(i), key1);
            }
        }

        return map;
    }




}
