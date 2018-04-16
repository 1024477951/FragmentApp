package com.fragmentapp.contacts.bean;

/**
 * Created by Bryan on 2017/8/24.
 */

public class UserInfo {
    private String userTitle;
    private String userCont;

    public UserInfo(String userTitle, String userCont) {
        this.userTitle = userTitle;
        this.userCont = userCont;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public String getUserCont() {
        return userCont;
    }

    public void setUserCont(String userCont) {
        this.userCont = userCont;
    }
}
