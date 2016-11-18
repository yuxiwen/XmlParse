package com.hcss.xml.model;
/**
 * @description
 *
 * @author 余锡文(yuxw@zjhcsoft.com)
 *
 * @version XmlTableModel.java v 1.0 2016年11月9日 下午4:59:53
 *
 */
public class XmlSqlModel {

    private String id;

    private String xml_type;

    private String xml_sql;

    private String orders;

    private String multiple;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXml_type() {
        return xml_type;
    }

    public void setXml_type(String xml_type) {
        this.xml_type = xml_type;
    }

    public String getXml_sql() {
        return xml_sql;
    }

    public void setXml_sql(String xml_sql) {
        this.xml_sql = xml_sql;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }


}
