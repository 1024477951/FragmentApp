package com.fragmentapp.contacts.userinfo;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fragmentapp.R;
import com.fragmentapp.contacts.bean.UserInfo;

/**
 * Created by Bryan on 2017/8/24.
 */

public class UserInfoAdapter  extends BaseQuickAdapter<UserInfo,BaseViewHolder> {
    public UserInfoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserInfo item) {
        helper.setText(R.id.tv_userTitle,item.getUserTitle()).setText(R.id.tv_userCont,item.getUserCont());
    }
}
