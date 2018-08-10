package com.fragmentapp.im.holder;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.fragmentapp.R;
import com.fragmentapp.helper.AudioPlayerUtils;
import com.fragmentapp.im.MessageStatus;
import com.fragmentapp.im.bean.MsgBean;

import java.util.HashMap;

/**
 * Created by liuzhen on 2018/8/7.
 */

public class IMVoiceHolder extends IMBaseHolder{

    private static HashMap<Integer, ImageView> mData = new HashMap<>();

    protected TextView tv_bg,tv_lenght;
    protected ImageView iv_anim;

    private AnimationDrawable anim;

    @Override
    protected void init(boolean isSelef) {
        tv_bg = getView(R.id.tv_bg);
        tv_lenght = getView(R.id.tv_lenght);
        iv_anim = getView(R.id.iv_anim);
    }

    @Override
    protected void status(int status) {
        switch (status) {//0:消息占位（在发送图片、文件等耗时任务是）1:发送中 2:发送成功 3:已收到 4:已读 5:撤销 10:发送失败 11:已删除
            case MessageStatus.SPACE:
            case MessageStatus.CREATED:
                pb_load.setVisibility(View.VISIBLE);
                btn_error.setVisibility(View.GONE);
                tv_read.setVisibility(View.GONE);
                break;
            case MessageStatus.SEND_ERROR:
                pb_load.setVisibility(View.GONE);
                tv_read.setVisibility(View.GONE);
                btn_error.setVisibility(View.VISIBLE);
                break;
            case MessageStatus.SEND_SUCCEED:
            case MessageStatus.RECEIVE_SUCCEED:
                pb_load.setVisibility(View.GONE);
                btn_error.setVisibility(View.GONE);
                tv_read.setVisibility(View.VISIBLE);
                tv_read.setText("已送达");
                break;
            case MessageStatus.READ:
                pb_load.setVisibility(View.GONE);
                btn_error.setVisibility(View.GONE);
                tv_read.setVisibility(View.VISIBLE);
                tv_read.setText("已读");
                break;
        }
    }

    /** 此处参数为 baseHolder view */
    public IMVoiceHolder(View view) {
        super(view);
    }

    @Override
    public void content(final MsgBean item){
        /**
         * 2秒及以内默认200px，到40秒每间隔10秒增进60px，40秒到60每隔10秒增进40px*/
        long duration = 2000;
        String lengthStr = duration + "2''";
        int maxWidth = 520;
        int minWidth = 200;
        int width;
        if (duration < 2){
            width = minWidth;
        }else if (duration > 2 && duration < 40){
            long moreDuration = (duration) / 10;
            width = 60 * (int)moreDuration + minWidth;
        }else{
            long moreDuration = (duration) / 10;
            width = 40 * (int)moreDuration + minWidth;
        }
        tv_bg.setWidth(width > maxWidth ? maxWidth : width);
        tv_lenght.setText(lengthStr);

        AudioPlayerUtils.newInstance().setCallBack(new AudioPlayerUtils.CallBack() {
            @Override
            public void stop() {
                for (ImageView iv : mData.values()) {
                    AnimationDrawable anim = (AnimationDrawable) iv.getBackground();
                    anim.selectDrawable(0);      //选择当前动画的第一帧，然后停止
                    anim.stop();
                }
                mData.clear();
                if (anim != null){
                    anim.stop();
                    anim.selectDrawable(0);
                    anim = null;
                }
//                KLog.json("Player","stop "+ JSONArray.toJSONString(mData.keySet()));
            }

            @Override
            public void start() {

            }
        });

        btn_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.click(v,item);
                }
            }
        });
        layout_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.click(v,item);
                }
            }
        });
        layout_content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClickListener != null) {
                    longClickListener.longClick(v, item);
                }
                return true;
            }
        });
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.click(v,item);
                }
            }
        });
    }

    public void playVoice(MsgBean message) {
        if (anim != null){
            anim.stop();
            anim.selectDrawable(0);
            anim = null;
        }
        anim = (AnimationDrawable) iv_anim.getBackground();
        anim.start();
        mData.put(message.getId(),iv_anim);

//        KLog.e("Player","start "+message.getMessageUId());
//        AudioPlayerUtils.newInstance().playBase64(voiceMsg.getBase64());
    }


}
