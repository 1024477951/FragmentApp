package com.fragmentapp.emoji.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fragmentapp.R;
import com.fragmentapp.emoji.bean.EmojiItem;
import com.fragmentapp.helper.GlideAppUtils;

/**
 * Created by liuzhen on 2018/11/30.
 */

public class EmojiAdapter extends BaseQuickAdapter<EmojiItem,BaseViewHolder>{

    public EmojiAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, EmojiItem item) {
//        GlideAppUtils.load(
//                (ImageView) helper.getView(R.id.iv_img),
//                StickerManager.getInstance().getStickerUri(item.getCategory(), item.getName())
//        );
        GlideAppUtils.load(
                (ImageView) helper.getView(R.id.iv_img),
                "file:///android_asset/" + item.assetPath
        );
    }

}
