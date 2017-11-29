package com.fragmentapp.login.imple;

import com.fragmentapp.http.BaseObserver;
import com.fragmentapp.http.BaseResponses;
import com.fragmentapp.login.bean.PersonBean;

/**
 * Created by liuzhen on 2017/11/7.
 */

public interface ILoginModel {

    void login(BaseObserver<BaseResponses> observer);
    void save(BaseObserver<BaseResponses> observer);

}
