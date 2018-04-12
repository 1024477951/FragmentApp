package com.fragmentapp.helper;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fragmentapp.App;

import java.io.File;

import cn.jiguang.imui.chatinput.camera.loader.ImageLoader;

/**
 * Created by liuzhen on 2018/4/12.
 */

public class GlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {

        GlideApp.with(App.getInstance().getApplicationContext())
                .load(Uri.fromFile(new File(path)))
                .override(width, height)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        GlideApp.with(App.getInstance().getApplicationContext())
                .load(Uri.fromFile(new File(path)))
                .override(width, height)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {
        //这里是清除缓存的方法,根据需要自己实现
    }

}
