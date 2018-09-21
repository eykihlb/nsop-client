package com.mydao.nsop.client.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static boolean compareTwoDate(String time){
        int diff = getIntervalDaysToNow(time);
        boolean flag = false;
        if(diff>=15){
            flag = true;
        }
        return flag;

    }

    /**
     * 得到某一日期与当前时间相差的天数
     * 日期格式为yyyy-MM-dd
     */
    public  static int getIntervalDaysToNow(String dateTime){
        if(dateTime.length()>10){
            dateTime=dateTime.substring(0,10);
        }
        int days=(int)(getS(dateToShortCode(new Date()))/(24*60*60*1000)-getS(dateTime)/(24*60*60000));
        return days;
    }

    /**
     * 返回指定Date值的时间值，格式：yyyy-MM-dd
     *
     * @param date
     *            需要转换的日期
     * @return String值
     */
    public static String dateToShortCode(Date date) {
        try {
            SimpleDateFormat simpledateformat = new SimpleDateFormat(
                    "yyyy-MM-dd");
            String s = simpledateformat.format(date);
            return s;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 转换日期字符串为long值，字符串格式：yyyy-MM-dd
     *
     * @param s
     *            需要转换的字符串，字符串格式：yyyy-MM-dd
     * @return long值
     */
    public static long getS(String s) {
        try {
            SimpleDateFormat simpledateformat = new SimpleDateFormat(
                    "yyyy-MM-dd");
            ParsePosition parseposition = new ParsePosition(0);
            Date date = simpledateformat.parse(s, parseposition);
            return date.getTime();
        } catch (Exception e) {
            return -1;
        }
    }
}
