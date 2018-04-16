package com.fragmentapp.contacts.bean;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.fragmentapp.contacts.teamgroup.TeamAdapter;

import java.util.List;

/**
 * Created by Bryan on 2017/8/23.
 */

public class UserGroupListDataBean extends AbstractExpandableItem<UserGroupListDataBean.UserListBean> implements MultiItemEntity,Comparable<UserGroupListDataBean> {


    private String team_id;
    private String team_name;
    private String team_type;
    private String team_sign;
    private List<UserListBean> userList;

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getTeam_type() {
        return team_type;
    }

    public void setTeam_type(String team_type) {
        this.team_type = team_type;
    }

    public String getTeam_sign() {
        return team_sign;
    }

    public void setTeam_sign(String team_sign) {
        this.team_sign = team_sign;
    }

    public List<UserListBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListBean> userList) {
        this.userList = userList;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return TeamAdapter.TYPE_TEAM;
    }

    @Override
    public int compareTo(@NonNull UserGroupListDataBean o) {
        int size;
        int osize;
        if (this.getUserList() != null) {
            size=this.getUserList().size();
        }else {
            size=0;
        }

        if (o.getUserList() != null) {
            osize=o.getUserList().size();
        }else {
            osize=0;
        }
        return Long.valueOf(osize).compareTo(Long.valueOf(size));
    }

    public static class UserListBean implements MultiItemEntity {


        private String uid;
        private String full_name;
        private String avatar;
        private String post_name;
        private String team_name;
        private String num;
        private String postion;

        public String getPostion() {
            return postion;
        }

        public void setPostion(String postion) {
            this.postion = postion;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getPost_name() {
            return post_name;
        }

        public void setPost_name(String post_name) {
            this.post_name = post_name;
        }

        public String getTeam_name() {
            return team_name;
        }

        public void setTeam_name(String team_name) {
            this.team_name = team_name;
        }

        @Override
        public int getItemType() {
            return TeamAdapter.TYPE_USER;
        }
    }

}
