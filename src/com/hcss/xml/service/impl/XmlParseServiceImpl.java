package com.hcss.xml.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hcss.util.XmlParseUtils;
import com.hcss.xml.biz.XmlParseBiz;
import com.hcss.xml.dao.DataSourceFactory;
import com.hcss.xml.service.XmlParseService;

/**
 * @description
 *
 * @author 余锡文(yuxw@zjhcsoft.com)
 *
 * @version XmlParseServiceImpl.java v 1.0 2016年11月17日 下午3:20:34
 *
 */
public class XmlParseServiceImpl implements XmlParseService {

    private static Log logger = LogFactory.getLog(XmlParseServiceImpl.class);

    protected static final DataSource dataSource = DataSourceFactory.createDataSource();

    @Override
    public Map<String, Object> getDataByInputStream(InputStream inputStream) {

        if (inputStream == null) {

            logger.error("入参为空！");

            return null;
        }

        return XmlParseUtils.parse(inputStream);
    }

    @Override
    public Map<String, Object> getDataByFile(File file) {

        if (file == null) {

            logger.error("入参为空！");

            return null;
        }

        return XmlParseUtils.parse(file);
    }


    @Override
    public void batchExecByFiles(List<File> files) {

        if (CollectionUtils.isEmpty(files)) {

            logger.error("入参为空！");

            return;
        }

        for(File file : files) {

            execByFile(file);
        }
    }

    @Override
    public void execByFile(File file) {

        if (file == null) {

            logger.error("入参为空！");

            return;
        }

        XmlParseBiz xmlParseBiz = new XmlParseBiz(dataSource);

        xmlParseBiz.execByFile(file);

    }

    @Override
    public void batchExecByInputStream(List<InputStream> inputStreams) {

        if (CollectionUtils.isEmpty(inputStreams)) {

            logger.error("入参为空！");

            return;
        }

        for (InputStream inputStream : inputStreams) {

            execByInputStream(inputStream);
        }

    }

    @Override
    public void execByInputStream(InputStream inputStream) {

        if (inputStream == null) {

            logger.error("入参为空！");

            return;
        }

        XmlParseBiz xmlParseBiz = new XmlParseBiz(dataSource);

        xmlParseBiz.execByInputStream(inputStream);

    }




}
