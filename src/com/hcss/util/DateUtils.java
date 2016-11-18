package com.hcss.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description 
 *
 * @author 余锡文(yuxw@zjhcsoft.com)
 *
 * @version DateUtils.java v 1.0 2016年11月8日 上午11:46:45
 *
 */
public class DateUtils {
	// 格式：年－月－日 小时：分钟：秒
    public static final String FORMAT_yMdHms = "yyyy-MM-dd HH:mm:ss";
	
	 /**
	  * 把符合日期格式的字符串转换为日期类型
	  * @param dateStr 日期字符串
	  * @param format 转换格式
	  * @return
	  */
    public static Date stringToDate(String dateStr, String format) {

        Date d = null;

        SimpleDateFormat formater = new SimpleDateFormat(format);
        
        try {
            formater.setLenient(false);

            d = formater.parse(dateStr);

        } catch (Exception e) { 

        	e.printStackTrace();

        }
        return d;
    }
	  
    /**
     * 两个日期相减
     * @param firstTime
     * @param secTime
     * @return 两个日期相减得到的秒数
     */
    public static long getTimeDiff(String firstTime, String secTime) {

        long first = stringToDate(firstTime, FORMAT_yMdHms).getTime();

        long second = stringToDate(secTime, FORMAT_yMdHms).getTime();

        return (second - first) / 1000;
    }

}
