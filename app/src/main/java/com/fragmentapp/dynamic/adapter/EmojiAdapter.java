package com.fragmentapp.dynamic.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fragmentapp.R;
import com.fragmentapp.view.emoji.EmotionUtils;

/**
 * Created by liuzhen on 2018/11/30.
 */

public class EmojiAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

    public EmojiAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setImageResource(R.id.iv_img, EmotionUtils.getImgByName(item));
    }
}
