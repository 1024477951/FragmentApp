package com.fragmentapp.helper;

import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fragmentapp.App;
import com.fragmentapp.R;

/**
 * Created by liuzhen on 2019/1/25.
 */

public class GlideAppUtils {

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

}
