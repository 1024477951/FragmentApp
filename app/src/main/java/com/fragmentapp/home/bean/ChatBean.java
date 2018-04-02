package com.fragmentapp.home.bean;

import android.support.annotation.NonNull;

import com.fragmentapp.chat.models.MyMessage;

import java.util.Calendar;

/**
 * Created by liuzhen on 2018/3/22.
 */

public class ChatBean implements Comparable<ChatBean> {

    public static final int Group = 1;
    public static final int Single = 2;
    public static final int Meet = 3;
    public static final int Leaves = 4;
    public static final int File = 5;

    private int id;

    private int top;

    /**
     * 置顶时间
     **/
    private long time;

    private String tag;

    private int type;

    private MyMessage lastMsg;

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MyMessage getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(MyMessage lastMsg) {
        this.lastMsg = lastMsg;
    }

    /**
     * 根据时间对比
     */
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
            if (result == 0) {
                /** 按时间排序 */
                result = 0 - compareToTime(time, chatBean.getTime());
            }
        }
        return result;
    }
}
