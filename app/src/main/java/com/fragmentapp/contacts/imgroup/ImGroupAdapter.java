package com.fragmentapp.contacts.imgroup;

import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fragmentapp.App;
import com.fragmentapp.R;
import com.fragmentapp.contacts.bean.IMUserGroupListDataBean;
import com.fragmentapp.helper.GlideApp;

/**
 * Created by Bryan on 2017/10/23.
 */

public class ImGroupAdapter extends BaseQuickAdapter<IMUserGroupListDataBean,BaseViewHolder> {


    public ImGroupAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, IMUserGroupListDataBean item) {
        final IMUserGroupListDataBean userListBean = (IMUserGroupListDataBean)item;
        GlideApp.with(App.getInstance().getApplicationContext())
                .load(userListBean.getAvatar())
                .circleCrop()
                .centerCrop()
                .transform(new RoundedCorners(16))
                .into((ImageView) helper.getView(R.id.iv_teamAvatar));

        helper.setText(R.id.tv_team_userName,userListBean.getGroup_name());

    }

    public void clean(){
        mData.clear();
        notifyDataSetChanged();
    }
}
