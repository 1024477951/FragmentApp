package com.fragmentapp.helper;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fragmentapp.App;
import com.fragmentapp.R;

/**
 * Created by liuzhen on 2019/1/25.
 */

public class GlideAppUtils {

//    public static void load(ImageView imageView,String path,int newState){
//        if (newState == RecyclerView.SCROLL_STATE_IDLE) {//空闲状态
//            GlideApp.with(App.getInstance()).resumeRequests();
//        }else {
//            GlideApp.with(App.getInstance()).pauseRequests();
//        }
//        if (path != null) {
//            GlideApp.with(App.getInstance())
//                    .load(path)
//                    .skipMemoryCache(false)
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .dontAnimate()
//                    .into(imageView);
//        }
//    }

    public static void load(ImageView imageView,String path){
        if (path != null) {
            GlideApp.with(App.getInstance())
                    .load(path)
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .dontAnimate()
                    .into(imageView);
        }
    }

    public static void load(ImageView imageView,Drawable drawable){
        if (drawable != null) {
            GlideApp.with(App.getInstance())
                    .load(drawable)
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .dontAnimate()
                    .into(imageView);
        }
    }

}
