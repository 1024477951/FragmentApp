package com.fragmentapp.login.imple;

import com.fragmentapp.login.bean.LoginDataBean;

/**
 * Created by liuzhen on 2017/11/6.
 */

public interface ILoginView {

    void success(LoginDataBean dataBean);
    void error();

}
