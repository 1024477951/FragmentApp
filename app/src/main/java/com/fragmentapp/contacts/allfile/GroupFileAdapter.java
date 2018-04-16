package com.fragmentapp.contacts.allfile;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fragmentapp.R;
import com.fragmentapp.contacts.bean.GroupFileBean;
import com.fragmentapp.contacts.bean.MessageData;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by liuzhen on 2018/3/14.
 */

public class GroupFileAdapter extends BaseQuickAdapter<MessageData.ListBean,BaseViewHolder> {

    public GroupFileAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageData.ListBean item) {

        GroupFileBean bean = null;
        if (item == null){
            bean = new GroupFileBean();
            bean.setFilename("异常文件");
        }
        if (item != null && item.getContent() != null) {
            String val = Html.fromHtml(item.getContent()).toString();
            if (val != null && !val.isEmpty()) {
                bean = getContentData(val);
                if (bean != null) {
                    helper.setText(R.id.tv_name, bean.getFilename());
                    helper.setText(R.id.tv_size, bean.getFileSizeM());
                }
                helper.setText(R.id.tv_time, item.getTimeline() + " 来自于" + item.getFrom_name()+"-"+item.getTeam_name());
            }
        }
        setIcon(bean.getFilename(), (ImageView) helper.getView(R.id.img_icon));
    }

    private void setIcon(String fileName, ImageView icon){
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
        int resId;
        switch (suffix.toLowerCase()){
            case "docx":
                resId = R.mipmap.icon_file_docx;
                break;
            case "jpg": case "9": case "png": case "gif":
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

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

        View view = holder.getView(R.id.group_line);
        if (view == null) return;
        if (position < getData().size()) {
            MessageData.ListBean nextBean = getItem(position + 1);
            MessageData.ListBean bean = getItem(position);
            if (nextBean != null && bean != null) {
                if (!bean.getGroupName().equals(nextBean.getGroupName())) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }
            }else{
                view.setVisibility(View.GONE);
            }
        }else{
            view.setVisibility(View.GONE);
        }
    }

    private GroupFileBean getContentData(String json){
        GroupFileBean bean = new Gson().fromJson(json,GroupFileBean.class);
        return bean;
    }

    public GroupFileBean getBean(int position){
        MessageData.ListBean bean = getItem(position);
        String json = bean.getContent();
        if (json == null || json.isEmpty()) return null;

        return getContentData(json);
    }

    public void clear(){
        mData.clear();
        notifyDataSetChanged();
    }

}