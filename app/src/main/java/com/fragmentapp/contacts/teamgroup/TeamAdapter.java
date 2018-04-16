package com.fragmentapp.contacts.teamgroup;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.fragmentapp.App;
import com.fragmentapp.R;
import com.fragmentapp.contacts.bean.UserGroupListDataBean;
import com.fragmentapp.helper.GlideApp;
import com.fragmentapp.helper.KLog;
import com.fragmentapp.helper.ResourcesUtil;

import java.util.List;

/**
 * Created by Bryan on 2017/8/23.
 */

public class TeamAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_TEAM = 0;
    public static final int TYPE_USER = 1;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public TeamAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_TEAM, R.layout.item_teamlist);
        addItemType(TYPE_USER, R.layout.item_teamlist_user);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_TEAM:
                final UserGroupListDataBean userGroupListDataBean = (UserGroupListDataBean)item;
                helper.setText(R.id.tv_teamName,userGroupListDataBean.getTeam_name())
                        .setText(R.id.tv_userNum,userGroupListDataBean.getUserList().size()+"");
                if (userGroupListDataBean.isExpanded()){
                    GlideApp.with(App.getInstance().getApplicationContext())
                            .load(R.mipmap.contact_list_arrow_pre)
                            .override((int) ResourcesUtil.dip2px((float) 18.5),(int) ResourcesUtil.dip2px(11))
                            .circleCrop()
                            .into((ImageView) helper.getView(R.id.iv_contact_list_arrow_nor));
                }else {
                    GlideApp.with(App.getInstance().getApplicationContext())
                            .load(R.mipmap.contact_list_arrow_nor)
                            .override((int) ResourcesUtil.dip2px((float) 10.5),(int) ResourcesUtil.dip2px(18))
                            .circleCrop()
                            .into((ImageView) helper.getView(R.id.iv_contact_list_arrow_nor));
                }


                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = helper.getAdapterPosition();
                        KLog.d(TAG, "UserGroupListDataBean" + pos);
                        if (userGroupListDataBean.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case TYPE_USER:
                final UserGroupListDataBean.UserListBean userListBean = (UserGroupListDataBean.UserListBean)item;
                GlideApp.with(App.getInstance().getApplicationContext())
                        .load(userListBean.getAvatar())
                        .circleCrop()
                        .centerCrop()
                        .transform(new RoundedCorners(16))
                        .into((ImageView) helper.getView(R.id.iv_teamAvatar));
                if (userListBean.getPost_name() != null) {
                    if (userListBean.getTeam_name() !=null){
                        helper.setText(R.id.tv_team_userName,userListBean.getFull_name()).setText(R.id.tv_team_userJob,userListBean.getPost_name());
                    }else {
                        helper.setText(R.id.tv_team_userName,userListBean.getFull_name()).setText(R.id.tv_team_userJob,userListBean.getPost_name());
                    }
                }else {
                    helper.setText(R.id.tv_team_userName,userListBean.getFull_name()).setText(R.id.tv_team_userJob,"");
                }

                break;
        }
    }

    public boolean isExpanded(int postion) {
        if (mData.size()==0){
            return false;
        }
        if (getItemViewType(postion)==TeamAdapter.TYPE_TEAM){
            UserGroupListDataBean userGroupListDataBean = (UserGroupListDataBean)getData().get(postion);
            if (userGroupListDataBean.isExpanded()) {
                return true;
            } else {
                return false;
            }
        }else {
            return false;
        }
    }

    public void clean(){
        mData.clear();
        notifyDataSetChanged();
    }
}
