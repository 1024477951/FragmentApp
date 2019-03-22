package com.fragmentapp.view.emoji;

import android.content.res.TypedArray;
import androidx.collection.ArrayMap;

import com.fragmentapp.App;
import com.fragmentapp.R;

/**
 * Created by Bryan on 2017/9/4.
 */

public class EmotionUtils {

    public static void clean(){
        if (EMPTY_MAP != null) {
            EMPTY_MAP.clear();
        }
    }
    /**
     * key-表情文字;
     * value-表情图片资源
     */
    public static ArrayMap<String, Integer> EMPTY_MAP;

    static {
        EMPTY_MAP = new ArrayMap<>();

        String[] rc_myemoji_code = App.getInstance().getResources().getStringArray(R.array.rc_myemoji_code);
        TypedArray array = App.getInstance().getResources().obtainTypedArray(R.array.rc_myemoji_res);
        for (int i = 0; i < rc_myemoji_code.length; i++) {
            EMPTY_MAP.put(rc_myemoji_code[i], array.getResourceId(i, -1));
        }
    }

    /**
     * 根据名称获取当前表情图标R值
     * @param imgName     名称
     * @return
     */
    public static int getImgByName(String imgName) {
        Integer integer = EMPTY_MAP.get(imgName);
        return integer == null ? -1 : integer;
    }
}
