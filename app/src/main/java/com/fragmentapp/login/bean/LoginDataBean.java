package com.fragmentapp.login.bean;


import com.google.gson.annotations.SerializedName;

/**
 * Created by liuzhen on 2017/8/15.
 */

public class LoginDataBean {

    @SerializedName("HC-ACCESS-TOKEN")
    private String HCACCESSTOKEN;

    public String getHCACCESSTOKEN() {
        return HCACCESSTOKEN;
    }

    public void setHCACCESSTOKEN(String HCACCESSTOKEN) {
        this.HCACCESSTOKEN = HCACCESSTOKEN;
    }
}
