package com.fragmentapp.im.holder;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fragmentapp.App;
import com.fragmentapp.R;
import com.fragmentapp.helper.GlideApp;
import com.fragmentapp.im.adapter.IMAdapter;
import com.fragmentapp.im.bean.MsgBean;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by liuzhen on 2018/8/7.
 * baseHolder view 抽象方法，减少逻辑
 */

public abstract class IMBaseHolder extends BaseViewHolder {

    protected IMAdapter.IMClickListener clickListener;
    protected IMAdapter.IMLongClickListener longClickListener;

    protected TextView tv_time, tv_read, tv_name,tv_voice;
    protected Button btn_error;

    protected CircleImageView profile_image;

    protected View pb_load, iv_read,layout_voice,pb_voice_font;

    protected RelativeLayout layout_content;
    protected View layout_status;

    protected abstract void init(boolean isSelef);

    protected abstract void status(int status);

    protected abstract void content(MsgBean item);

    public IMBaseHolder(View view) {
        super(view);

        tv_time = getView(R.id.tv_time);
        btn_error = getView(R.id.btn_error);
        tv_read = getView(R.id.tv_read);
        tv_name = getView(R.id.tv_name);
        pb_load = getView(R.id.pb_load);
        tv_voice = getView(R.id.tv_voice);
        pb_voice_font = getView(R.id.pb_voice_font);

        layout_content = getView(R.id.layout_content);
        layout_status = getView(R.id.layout_status);
        layout_voice = getView(R.id.layout_voice);

        profile_image = getView(R.id.profile_image);
    }
    /**设置内容视图*/
    public void setContentView(View view, boolean isSelef) {
        layout_content.removeAllViews();
        layout_content.addView(view);
        init(isSelef);
        layout_status.setVisibility(View.GONE);
        tv_time.setVisibility(View.GONE);
        layout_voice.setVisibility(View.GONE);
        if (iv_read != null) {
            iv_read.setVisibility(View.GONE);
        }
    }

    public void setContent(MsgBean item) {
        if (tv_name != null) {
            tv_name.setText("tom");
        }
        if (layout_status != null) {
            layout_status.setVisibility(View.GONE);
        }
        if (profile_image != null) {
            GlideApp.with(App.getInstance())
                    .load(R.mipmap.icon_chat_single)
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(profile_image);
        }
        content(item);
    }

    /**
     * 单独类型设置样式
     */
    public void setContentBg() {
        int bg = R.drawable.icon_im_send_white_bg;
        if (layout_content != null) {
            layout_content.setBackgroundResource(bg);
        }
    }

    public void showMsgDate() {
        if (tv_time != null) {
            tv_time.setText("～以下为新消息～");
            tv_time.setVisibility(View.VISIBLE);
        }
    }

    public void showMsgLine(long time) {
        if (tv_time != null) {
            tv_time.setText("2018/08/10");
            tv_time.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 消息状态
     */
    public void setStatus(int status) {
        if (layout_status != null) {
            layout_status.setVisibility(View.VISIBLE);
            status(status);
        }
    }

    public void setClickListener(IMAdapter.IMClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setLongClickListener(IMAdapter.IMLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }
}
