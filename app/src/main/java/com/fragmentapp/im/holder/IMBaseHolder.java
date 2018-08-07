package com.fragmentapp.im.holder;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.fragmentapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by liuzhen on 2018/8/7.
 * baseHolder view
 */

public abstract class IMBaseHolder extends BaseViewHolder {

    protected TextView tv_time, tv_read;
    protected Button btn_error;
    protected View root;
    protected CircleImageView profile_image;

    protected RelativeLayout layout_content;
    protected View layout_status;

    protected abstract void init(View view,boolean isSelef);

    public IMBaseHolder(View view) {
        super(view);

        tv_time = getView(R.id.tv_time);
        btn_error = getView(R.id.btn_error);
        tv_read = getView(R.id.tv_read);

        root = getView(R.id.root);
        layout_content = getView(R.id.layout_content);
        layout_status = getView(R.id.layout_status);

        profile_image = getView(R.id.profile_image);
    }

    public void setContent(View view,boolean isSelef) {
        layout_content.removeAllViews();
        layout_content.addView(view);
        init(view,isSelef);
        if (isSelef){
            layout_status.setVisibility(View.VISIBLE);
            btn_error.setVisibility(View.GONE);
        }else{
            layout_status.setVisibility(View.GONE);
        }
    }
    /**单独类型设置样式*/
    public void setContentBg() {
        int bg = R.drawable.icon_im_send_white_bg;
        layout_content.setBackgroundResource(bg);
    }

}
