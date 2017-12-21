package com.fragmentapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.fragmentapp.App;

/**
 * Created by liuzhen on 2017/12/21.
 */

public class SharedPreferencesUtils {

    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "com.fragmentapp.App.share_date";


    public static void setParam(String key, String val){
        SharedPreferences sp = App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, val);
        editor.commit();
    }
    public static void setParam(String key, int val){
        SharedPreferences sp = App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, val);
        editor.commit();
    }
    public static void setParam(String key, boolean val){
        SharedPreferences sp = App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, val);
        editor.commit();
    }
    public static void setParam(String key, float val){
        SharedPreferences sp = App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, val);
        editor.commit();
    }
    public static void setParam(String key, long val){
        SharedPreferences sp = App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, val);
        editor.commit();
    }


    public static String getParam(String key, String defVal){
        SharedPreferences sp = App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defVal);
    }
    public static String getParam(String key){
        SharedPreferences sp = App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, null);
    }

    public static int getParam(String key, int defVal){
        SharedPreferences sp = App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defVal);
    }
    public static float getParam(String key, float defVal){
        SharedPreferences sp = App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getFloat(key, defVal);
    }
    public static long getParam(String key, long defVal){
        SharedPreferences sp = App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getLong(key, defVal);
    }
    public static boolean getParam(String key, boolean defVal){
        SharedPreferences sp = App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defVal);
    }


}