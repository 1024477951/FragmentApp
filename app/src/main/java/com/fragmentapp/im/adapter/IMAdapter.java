package com.fragmentapp.im.adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fragmentapp.R;
import com.fragmentapp.im.MessageStatus;
import com.fragmentapp.im.bean.MsgBean;
import com.fragmentapp.im.bean.MsgType;
import com.fragmentapp.im.holder.IMBaseHolder;
import com.fragmentapp.im.holder.IMImageTextHolder;
import com.fragmentapp.im.holder.IMPhotoHolder;
import com.fragmentapp.im.holder.IMTextHolder;

import java.util.List;

/**
 * Created by liuzhen on 2018/8/6.
 */

public class IMAdapter extends BaseQuickAdapter<MsgBean, IMBaseHolder> {

    protected IMAdapter.IMClickListener clickListener;
    protected IMAdapter.IMLongClickListener longClickListener;

    public IMAdapter(@Nullable List<MsgBean> data) {
        super(data);
    }

    @Override
    public int getItemViewType(int position) {//此处需要的是msg type
        MsgBean entity = getItem(position);
        int type = 0;
        switch (entity.getType()){
            case MsgBean.Text:
                if (entity.isSelf()){
                    type = MsgType.TYPE_SEND_TXT;
                }else{
                    type = MsgType.TYPE_RECEIVE_TXT;
                }
                break;
            case MsgBean.Photo:
                if (entity.isSelf()){
                    type = MsgType.TYPE_SEND_PHOTO;
                }else{
                    type = MsgType.TYPE_RECEIVE_PHOTO;
                }
                break;
            case MsgBean.Image_Text:
                if (entity.isSelf()){
                    type = MsgType.TYPE_SEND_IMGTEXT;
                }else{
                    type = MsgType.TYPE_RECEIVE_IMGTEXT;
                }
                break;
        }
        return type;//返回细分类型
    }

    /**
     * 自定义viewholder,抽离原本布局复用，也可样式分离，比如 [语音]
     * */
    @Override
    public IMBaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        IMBaseHolder holder = null;
        View view = null;
        boolean isSelf = false;
        switch (viewType) {
            case MsgType.TYPE_SEND_TXT:
                isSelf = true;
                view = inflater.inflate(R.layout.item_im_text, parent,false);
                holder = new IMTextHolder(inflater.inflate(R.layout.item_im_send_holder, parent,false));
                break;
            case MsgType.TYPE_RECEIVE_TXT:
                view = inflater.inflate(R.layout.item_im_text, parent,false);
                holder = new IMTextHolder(inflater.inflate(R.layout.item_im_receive_holder, parent,false));
                break;
            case MsgType.TYPE_SEND_PHOTO:
                isSelf = true;
                view = inflater.inflate(R.layout.item_im_photo, parent,false);
                holder = new IMPhotoHolder(inflater.inflate(R.layout.item_im_send_holder, parent,false));
                break;
            case MsgType.TYPE_RECEIVE_PHOTO:
                view = inflater.inflate(R.layout.item_im_photo, parent,false);
                holder = new IMPhotoHolder(inflater.inflate(R.layout.item_im_receive_holder, parent,false));
                break;
            case MsgType.TYPE_SEND_IMGTEXT:
                isSelf = true;
                view = inflater.inflate(R.layout.item_im_imagetext, parent,false);
                holder = new IMImageTextHolder(inflater.inflate(R.layout.item_im_send_holder, parent,false));
                break;
            case MsgType.TYPE_RECEIVE_IMGTEXT:
                view = inflater.inflate(R.layout.item_im_imagetext, parent,false);
                holder = new IMImageTextHolder(inflater.inflate(R.layout.item_im_receive_holder, parent,false));
                break;
        }
        holder.setContentView(view,isSelf);//给通用视图填充多类型视图
        return holder;
    }

    /**
     * 抽象适配器方法，重复利用，减少比对
     * 避免多类型时类型转换繁琐操作，直接抽象成base
     * */
    @Override
    protected void convert(IMBaseHolder helper, MsgBean item) {
        if (helper == null) return;

        helper.setContent(item);//把通用的操作放在base里

        boolean isShowDate = false;
        boolean whiteBg = false;
        switch (item.getType()) {
            case MsgBean.Image_Text:
                whiteBg = true;
                break;
            case MsgBean.File:
                whiteBg = true;
                break;
        }

        if (item.isSelf() && whiteBg){//只有发送样式不同
            helper.setContentBg();
        }
        if (isShowDate){
            helper.showMsgLine(0);//显示历史消息样式
        }

        if (item.isSelf()) {
            helper.setStatus(MessageStatus.READ);//状态固定为已读
        }

        helper.setClickListener(clickListener);
        helper.setLongClickListener(longClickListener);
    }

    public interface IMClickListener {
        void click(View view,MsgBean message);
    }

    public interface IMLongClickListener {
        void longClick(View view, MsgBean message);
    }

    public void setClickListener(IMClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setLongClickListener(IMLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }
}
