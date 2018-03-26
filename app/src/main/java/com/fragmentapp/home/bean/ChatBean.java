package com.fragmentapp.home.bean;

import android.support.annotation.NonNull;
import java.util.Calendar;

/**
 * Created by liuzhen on 2018/3/22.
 */

public class ChatBean implements Comparable<ChatBean> {

    private int id;

    private int top;

    /**
     * 置顶时间
     **/
    public long time;

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * 根据时间对比
     * */
    public int compareToTime(long lhs, long rhs) {
        Calendar cLhs = Calendar.getInstance();
        Calendar cRhs = Calendar.getInstance();
        cLhs.setTimeInMillis(lhs);
        cRhs.setTimeInMillis(rhs);
        return cLhs.compareTo(cRhs);
    }

    @Override
    public int compareTo(@NonNull ChatBean chatBean) {
        if (chatBean == null) {
            return -1;
        }
        /** 判断是否置顶 */
        int result = 0 - (top - chatBean.getTop());
        if (result == 0) {
            /** 按时间排序 */
            result = 0 - compareToTime(time, chatBean.getTime());
        }
        return result;
    }
}
