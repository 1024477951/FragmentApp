package com.fragmentapp.emoji.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fragmentapp.R;
import com.fragmentapp.emoji.bean.StickerItem;
import com.fragmentapp.emoji.manager.StickerManager;
import com.fragmentapp.helper.GlideAppUtils;

/**
 * Created by liuzhen on 2018/11/30.
 */

public class StickerAdapter extends BaseQuickAdapter<StickerItem,BaseViewHolder>{

    public StickerAdapter(int layoutResId) {
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
