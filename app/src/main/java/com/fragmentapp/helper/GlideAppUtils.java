package com.fragmentapp.helper;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.fragmentapp.App;
import com.fragmentapp.R;

import java.io.File;

/**
 * Created by liuzhen on 2018/4/18.
 */

public class GlideAppUtils {

    public static void defLoadImage(String url, ImageView imageView){
        GlideApp.with(App.getInstance().getApplicationContext())
                .load(url)
                .error(R.mipmap.empty)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(false)
//                .transform(new RoundedCorners(10))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.mipmap.icon_loading)
                .centerCrop()
                .into(imageView);
    }

    public static void defLocationImage(String path, ImageView imageView){
        GlideApp.with(App.getInstance().getApplicationContext())
                .load(Uri.fromFile(new File(path)))
                .error(R.mipmap.empty)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(false)
//                .transform(new RoundedCorners(10))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.mipmap.icon_loading)
                .centerCrop()
                .into(imageView);
    }

}
