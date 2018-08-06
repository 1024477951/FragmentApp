package com.fragmentapp.im.adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fragmentapp.R;
import com.fragmentapp.im.HolderHelper;
import com.fragmentapp.im.bean.IMEntity;
import com.fragmentapp.im.bean.MsgBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by liuzhen on 2018/8/6.
 */

public class IMAdapter extends BaseMultiItemQuickAdapter<IMEntity, IMAdapter.ViewHolder> {


    public IMAdapter(@Nullable List<IMEntity> data) {
        super(data);
        addItemType(IMEntity.Send, R.layout.item_im_receive_holder);
        addItemType(IMEntity.Receive, R.layout.item_im_send_holder);

    }

    @Override
    protected void convert(ViewHolder helper, IMEntity item) {
        switch (helper.getItemViewType()) {
            case IMEntity.Send:
                break;
            case IMEntity.Receive:
                break;
        }
        View view = HolderHelper.getInsatance().getHolderView(item.getData().getType());
        if (view != null) {
            helper.setContent(view);
        }else{
            HolderHelper.getInsatance().put(item.getData().getType());
            helper.setContent(view);
        }
    }

    public class ViewHolder extends BaseViewHolder
    {

        protected TextView tv_time,tv_read;
        protected Button btn_error;
        protected View root;
        protected CircleImageView profile_image;

        protected RelativeLayout layout_content;

        public ViewHolder(View view)
        {
            super(view);

            tv_time = getView(R.id.tv_time);
            btn_error = getView(R.id.btn_error);
            tv_read = getView(R.id.tv_read);

            root = getView(R.id.root);
            layout_content = getView(R.id.layout_content);

            profile_image = getView(R.id.profile_image);
        }

        protected void setContent(View view){
            layout_content.removeAllViews();
            layout_content.addView(view);
        }

    }

}
