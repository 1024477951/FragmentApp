package com.fragmentapp.helper;

import com.orhanobut.logger.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by liuzhen on 2018/1/5.
 */

public class TimeUtil {

    private static long defaultTime = 2000;
    private static Date startTime = null,endTime = null;

    public static void startTime(){
        clear();
//        Logger.d("tag","startTime");
        if (startTime == null)
            startTime = new Date();
    }

    public static void endTime(){
//        Logger.d("tag","endTime");
        if (endTime == null)
            endTime = new Date();
    }

    /**获取时间差毫秒数**/
    public static long getDateMillis() {
        if (startTime == null || endTime == null)
            return 0;
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endTime.getTime() - startTime.getTime();
//        // 计算差多少天
//        long day = diff / nd;
//        // 计算差多少小时
//        long hour = diff % nd / nh;
//        // 计算差多少分钟
//        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm;
        clear();
        return defaultTime - sec;
    }

    public static String getMonthDayHourTime(String timeStamp) throws ParseException {
        long lt = new Long(timeStamp);
        Date date = new Date(lt * 1000);
        DateFormat China_FORMAT = new SimpleDateFormat("MM月dd日 HH:mm", Locale.getDefault());
        return China_FORMAT.format(date);
    }

    public static String getHourTime(String timeStamp) throws ParseException {
        long lt = new Long(timeStamp);
        Date date = new Date(lt * 1000);
        DateFormat China_FORMAT = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return China_FORMAT.format(date);
    }

    public static String getMonthDayTime(String timeStamp) throws ParseException {
        long lt = new Long(timeStamp);
        Date date = new Date(lt * 1000);
        DateFormat China_FORMAT = new SimpleDateFormat("MM月dd日", Locale.getDefault());
        return China_FORMAT.format(date);
    }

    private static void clear(){
        startTime = null;
        endTime = null;
    }

}
