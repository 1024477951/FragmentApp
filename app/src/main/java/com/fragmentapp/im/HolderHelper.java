package com.fragmentapp.im;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.fragmentapp.App;
import com.fragmentapp.R;
import com.fragmentapp.im.bean.MsgBean;

import java.util.HashMap;

/**
 * Created by liuzhen on 2018/8/6.
 * 缓存类型视图
 */

public class HolderHelper {

    private static class SingletonHolder{
        private static final HolderHelper instance = new HolderHelper();
    }
    private HolderHelper(){}
    public static final HolderHelper getInsatance(){
        return SingletonHolder.instance;
    }

    private HashMap<Integer,View> map = new HashMap<>();

    public void put(int type){
        if (map.containsKey(type) == false) {
            View view = null;
            switch (type){
                case MsgBean.Text:
                    view = LayoutInflater.from(App.getInstance()).inflate(R.layout.item_im_text, null);
                    break;
                case MsgBean.Photo:
                    view = LayoutInflater.from(App.getInstance()).inflate(R.layout.item_im_photo, null);
                    break;
            }
            map.put(type, view);
        }
    }

    public View getHolderView(int type){
        View view = map.get(type);
        return view;
    }

    public void clear(){
        map.clear();
        map = null;
    }

}
