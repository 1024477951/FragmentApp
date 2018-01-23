package com.fragmentapp.view.beans;

import android.util.Log;

/**
 * Created by liuzhen on 2018/1/18.
 */

public class Beans {

    public Beans(){ }

    public Beans(int radius){
        this.radius = radius;
    }

    /**X坐标*/
    private float cx;
    /**Y坐标*/
    private float cy;
    /**偏移量*/
    private float off;
    /**随机生成的速度值*/
    private float rand;
    /**是否碰到边缘*/
    private int state;
    /**圆球的大小*/
    private float radius;

    /**移动 X 坐标，并且碰到边界后回弹*/
    public void move(int width){
        if (cx < 0 || state == 1) {//碰到左边的边缘
            state = 1;
            off += rand;
        } else if (cx >= width || state == 2) {//碰到右边的边缘
            state = 2;
            off -= rand;
        }else if(state == 0) {
            state = 0;
            off += rand;
        }
//        Log.e("tag","-- cx "+(int)cx  + " width "+width + " state "+state);
    }

    public float getCx() {
        return cx;
    }

    public void setCx(float cx) {
        this.cx = cx;
    }

    public float getOff() {
        return off;
    }

    public void setOff(float off) {
        this.off = off;
    }

    public float getRand() {
        return rand;
    }

    public void setRand(float rand) {
        this.rand = rand;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getCy() {
        return cy;
    }

    public void setCy(float cy) {
        this.cy = cy;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
