package com.fragmentapp.im.holder;

import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fragmentapp.App;
import com.fragmentapp.R;
import com.fragmentapp.im.MessageStatus;
import com.fragmentapp.im.bean.MsgBean;

import java.io.File;

/**
 * Created by liuzhen on 2018/8/7.
 */

public class IMFileHolder extends IMBaseHolder{

    protected ImageView iv_file;
    protected TextView tv_title,tv_size,tv_down;
    protected ProgressBar pb_down;
    protected View down_detail;

    @Override
    protected void init(boolean isSelef) {
        iv_file = getView(R.id.iv_file);
        tv_title = getView(R.id.tv_title);
        pb_down = getView(R.id.pb_down);
        tv_size = getView(R.id.tv_size);
        tv_down = getView(R.id.tv_down);
        down_detail = getView(R.id.down_detail);
        int txtColor;
        if (isSelef){
            txtColor = App.getInstance().getResources().getColor(R.color.color_4b4b4b);
        }else{
            txtColor = App.getInstance().getResources().getColor(R.color.color_4b4b4b);
        }
        tv_title.setTextColor(txtColor);
    }

    @Override
    protected void status(int status) {
        switch (status) {//0:消息占位（在发送图片、文件等耗时任务是）1:发送中 2:发送成功 3:已收到 4:已读 5:撤销 10:发送失败 11:已删除
            case MessageStatus.SPACE:
            case MessageStatus.CREATED:
                pb_load.setVisibility(View.VISIBLE);
                btn_error.setVisibility(View.GONE);
                tv_read.setVisibility(View.GONE);
                down_detail.setVisibility(View.GONE);
                pb_down.setVisibility(View.VISIBLE);
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
                down_detail.setVisibility(View.VISIBLE);
                pb_down.setVisibility(View.GONE);
                break;
            case MessageStatus.READ:
                pb_load.setVisibility(View.GONE);
                btn_error.setVisibility(View.GONE);
                tv_read.setVisibility(View.VISIBLE);
                tv_read.setText("已读");
                down_detail.setVisibility(View.VISIBLE);
                pb_down.setVisibility(View.GONE);
                break;
        }
    }

    public void status(boolean isDown,int progress){
        if (isDown){
            down_detail.setVisibility(View.VISIBLE);
            pb_down.setVisibility(View.GONE);
            tv_down.setText("预览");
        }else {
            if (progress == 0){
                down_detail.setVisibility(View.VISIBLE);
                pb_down.setVisibility(View.GONE);
                tv_down.setText("未下载");
            }else{
                down_detail.setVisibility(View.GONE);
                pb_down.setVisibility(View.VISIBLE);
                pb_down.setProgress(progress);
            }

        }
    }

    /** 此处参数为 baseHolder view */
    public IMFileHolder(View view) {
        super(view);
    }

    @Override
    public void content(final MsgBean item){
        tv_title.setText("这是一个测试文件.pdf");

        setIcon("这是一个测试文件.pdf", iv_file);
        File d = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/d/demo.pdf");
        if (d.exists()) {
            tv_down.setText("预览");
        }else{
            tv_down.setText("未下载");
        }
        tv_size.setText("? Mb");

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
        down_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                down_detail.setVisibility(View.GONE);
                pb_down.setVisibility(View.VISIBLE);
            }
        });
    }

    protected void setIcon(String fileName, ImageView icon){
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
        int resId;
        switch (suffix.toLowerCase()){
            case "docx":
                resId = R.mipmap.icon_file_docx;
                break;
            case "jpg": case "9": case "png": case "gif":case "jpeg":
                resId = R.mipmap.icon_file_img;
                break;
            case "link":
                resId = R.mipmap.icon_file_link;
                break;
            case "music":
                resId = R.mipmap.icon_file_music;
                break;
            case "pdf":
                resId = R.mipmap.icon_file_pdf;
                break;
            case "ppt": case "pptx":
                resId = R.mipmap.icon_file_ppt;
                break;
            case "video":
                resId = R.mipmap.icon_file_video;
                break;
            case "zip": case "7z":
                resId = R.mipmap.icon_file_zip;
                break;
            case "xlsx":
                resId = R.mipmap.icon_file_xlsx;
                break;
            default:
                resId = R.mipmap.icon_file_other;
                break;
        }
        icon.setImageResource(resId);
    }

}
