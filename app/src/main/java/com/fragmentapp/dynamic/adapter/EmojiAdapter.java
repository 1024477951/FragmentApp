package com.fragmentapp.dynamic.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fragmentapp.R;
import com.fragmentapp.emoji.StickerItem;
import com.fragmentapp.emoji.StickerManager;
import com.fragmentapp.helper.GlideAppUtils;

/**
 * Created by liuzhen on 2018/11/30.
 */

public class EmojiAdapter extends BaseQuickAdapter<StickerItem,BaseViewHolder>{

    public EmojiAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, StickerItem item) {
        GlideAppUtils.load(
                (ImageView) helper.getView(R.id.iv_img),
                StickerManager.getInstance().getStickerUri(item.getCategory(), item.getName())
        );
    }
}
