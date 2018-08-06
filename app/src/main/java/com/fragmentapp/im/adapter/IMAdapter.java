package com.fragmentapp.im.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fragmentapp.R;
import com.fragmentapp.im.bean.IMEntity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by liuzhen on 2018/8/6.
 */

public class IMAdapter extends BaseMultiItemQuickAdapter<IMEntity, IMAdapter.ViewHolder> {


    public IMAdapter(@Nullable List<IMEntity> data) {
        super(data);
        addItemType(IMEntity.Send, R.layout.item_im_send_holder);
        addItemType(IMEntity.Receive, R.layout.item_im_receive_holder);
    }

    @Override
    protected void convert(ViewHolder helper, IMEntity item) {
        switch (helper.getItemViewType()) {
            case IMEntity.Send:

                break;
            case IMEntity.Receive:

                break;
        }
    }

    static class ViewHolder extends BaseViewHolder
    {

        TextView tv_time,tv_read,tv_status;
        View root;
        CircleImageView profile_image;

        public ViewHolder(View view)
        {
            super(view);
            tv_time = getView(R.id.tv_time);
            tv_status = getView(R.id.tv_status);
            tv_read = getView(R.id.tv_read);

            root = getView(R.id.root);

            profile_image = getView(R.id.profile_image);
        }
    }

}
