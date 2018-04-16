package com.fragmentapp.helper;


import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.fragmentapp.App;

/**
 * Created by bryan on 2017/2/27.
 */

public class ResourcesUtil {

    public static String getString(int id) {
        return App.getInstance().getApplicationContext().getResources().getString(id);
    }

    public static String[] getStringArray(int id) {
        return App.getInstance().getApplicationContext().getResources().getStringArray(id);
    }
    public static int[] getIntArray(int id) {
        return App.getInstance().getApplicationContext().getResources().getIntArray(id);
    }

    public static TypedArray obtainTypedArray(int id) {
        return App.getInstance().getApplicationContext().getResources().obtainTypedArray(id);
    }
    public static Drawable getDrawable(int id) {
        return App.getInstance().getApplicationContext().getResources().getDrawable(id);
    }

    public static int getColor(int id) {
        return App.getInstance().getApplicationContext().getResources().getColor(id);
    }

    public static float getDimension(int id) {
        return App.getInstance().getApplicationContext().getResources().getDimension(id);
    }

    public static Bitmap getBitmap(int id){
        return BitmapFactory.decodeResource(App.getInstance().getApplicationContext().getResources(),id);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */

    public static float dip2px(float dpValue) {

        final float scale = App.getInstance().getApplicationContext().getResources().getDisplayMetrics().density;

        return dpValue * scale;

    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */

    public static float px2dip(float pxValue) {

        final float scale = App.getInstance().getApplicationContext().getResources().getDisplayMetrics().density;

        return pxValue / scale;

    }
}
