package com.fragmentapp.contacts.bean;

import java.io.Serializable;

/**
 * Created by Bryan on 2017/10/23.
 */

public class IMUserGroupListDataBean implements Serializable {

    public IMUserGroupListDataBean(String id, String group_name, String avatar, String owner_id) {
        this.id = id;
        this.group_name = group_name;
        this.avatar = avatar;
        this.owner_id = owner_id;
    }

    private String id;
    private String group_name;
    private String avatar;
    private String owner_id;





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }


}
