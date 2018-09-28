package com.fragmentapp.helper;

import com.fragmentapp.BuildConfig;

/**
 * Created by liuzhen on 2018/9/26.
 */

public class GuideUtil {

    public String main = "mainGuide";
    public String stars = "starsGuide";

    private GuideUtil(){}

    private static class Instance{
        static GuideUtil instance = new GuideUtil();
    }

    public static GuideUtil getInstance(){
        return Instance.instance;
    }

    public boolean isGuide(String key){
        boolean isGuide = false;
        if (BuildConfig.VERSION_CODE == 1 && SharedPreferencesUtils.getParam(key,false)){
            isGuide = true;
        }
        return true;
    }

}
