package com.hcss.xml.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @description
 *
 * @author 余锡文(yuxw@zjhcsoft.com)
 *
 * @version XmlParseService.java v 1.0 2016年11月17日 下午3:19:48
 *
 */
public interface XmlParseService {

    /**
     * 根据XML输入流解析数据
     *
     * @param inputStream
     * @return
     */
    public Map<String, Object> getDataByInputStream(InputStream inputStream);

    /**
     * 根据XML文件解析数据
     *
     * @param file
     * @return
     */
    public Map<String,Object> getDataByFile(File file);

    /**
     * 批量操作
     *
     * @param files
     */
    public void batchExecByFiles(List<File> files);

    /**
     * 根据XML文件执行数据库增删改操作
     *
     * @param file
     */
    public void execByFile(File file);

    /**
     * 批量操作
     *
     * @param inputStreams
     */
    public void batchExecByInputStream(List<InputStream> inputStreams);

    /**
     * 根据XML输入流执行数据库增删改操作
     *
     * @param inputStream
     */
    public void execByInputStream(InputStream inputStream);



}
