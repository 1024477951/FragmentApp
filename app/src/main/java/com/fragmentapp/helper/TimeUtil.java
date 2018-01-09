package com.fragmentapp.helper;

import com.orhanobut.logger.Logger;

import java.util.Date;

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

    private static void clear(){
        startTime = null;
        endTime = null;
    }

}
