package com.hcss.xml.biz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hcss.util.SqlParseUtils;
import com.hcss.util.XmlParseUtils;
import com.hcss.util.transction.TransactionManager;
import com.hcss.xml.dao.XmlParseDao;
import com.hcss.xml.model.XmlSqlModel;

/**
 * @description
 *
 * @author 余锡文(yuxw@zjhcsoft.com)
 *
 * @version XmlParseBiz.java v 1.0 2016年11月18日 上午10:25:39
 *
 */
public class XmlParseBiz {

    private static Log logger = LogFactory.getLog(XmlParseBiz.class);

    private XmlParseDao xmlParseDao;

    private TransactionManager transactionManager;

    public XmlParseBiz(DataSource dataSource) {

        xmlParseDao = new XmlParseDao(dataSource);

        transactionManager = new TransactionManager(dataSource);
    }

    /**
     * 根据XML文件执行执行数据库增删改操作
     *
     * @param file
     */
    public void execByFile(File file){

        try {
            // 解析XML文件
            Map<String,Object> dataMap = XmlParseUtils.parse(file);

            execute(dataMap);

        } catch (Exception e) {

            // 保存文件到本地
            saveToLocal(file, file.getName());

            logger.error("根据XML文件执行数据库增删改操作异常",e);
        }

    }

    /**
     * 根据XML输入流执行执行数据库增删改操作
     *
     * @param inputStream
     */
    public void execByInputStream (InputStream inputStream){

        try {
            // 解析XML文件
            Map<String,Object> dataMap = XmlParseUtils.parse(inputStream);

            execute(dataMap);

        } catch (Exception e) {

            saveToLocal(inputStream, "");

            logger.error("根据XML文件执行数据库增删改操作异常",e);
        }

    }

    /**
     * 根据XML数据Map执行数据库增删改操作
     *
     * @param file
     */
    public void execute(Map<String, Object> dataMap) {

        try {

            transactionManager.start();

            // XML格式名称
            String xmlType = (String) dataMap.get("MSG.META.STYP");

            // 去掉key的前缀
            dataMap = SqlParseUtils.parseMap(xmlParseDao,dataMap,xmlType);

            // 解析后的sql语句列表
            List<String> execSqls = new ArrayList<String>();

            // 获取待解析的sql语句
            List<XmlSqlModel> xmlSqlList = xmlParseDao.select("SELECT ID,XML_TYPE,XML_SQL,ORDERS,MULTIPLE FROM HCSS_XML_SQL WHERE XML_TYPE = '" + xmlType + "' ORDER BY ORDERS", XmlSqlModel.class);

            for (XmlSqlModel xmlSqlModel : xmlSqlList) {

                if(StringUtils.equals("Y", xmlSqlModel.getMultiple())) {

                    execSqls.addAll(SqlParseUtils.multiParse(xmlSqlModel.getXml_sql(), dataMap));

                } else {

                    execSqls.add(SqlParseUtils.singleParse(xmlSqlModel.getXml_sql(), dataMap));
                }
            }

            // 顺序执行待执行的sql语句
            for (String execSql : execSqls) {

                xmlParseDao.execute(execSql);

            }

            transactionManager.commit();

        } catch (Exception e) {

            transactionManager.rollback();

            logger.error("根据XML数据Map执行数据库增删改操作异常",e);

        } finally {

            transactionManager.close();
        }
    }

    /**
     * 保存XML文件到本地
     *
     * @param file
     */
    private void saveToLocal(File file, String fileName) {

        try {
            FileInputStream inputStream = new FileInputStream(file);

            saveToLocal(inputStream, fileName);

        } catch (FileNotFoundException e) {

            logger.error("根据文件生成输入流异常，文件名：" + fileName, e);
        }

    }

    /**
     * 保存XML输入流到本地
     *
     * @param inputStream
     * @param fileName
     */
    private void saveToLocal(InputStream inputStream, String fileName) {

        OutputStream os = null;

        try {

            String path = "D:\\cachedFile\\";

            // 保存到临时文件,1K的数据缓冲
            byte[] bs = new byte[1024];

            // 读取到的数据长度
            int len;

            // 输出的文件流保存到本地文件
            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);

            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

        } catch (Exception e) {

            logger.error("保存执行失败的输入流为本地文件异常",e);

        } finally {

            // 完毕，关闭所有链接
            try {
                os.close();

                inputStream.close();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

}
