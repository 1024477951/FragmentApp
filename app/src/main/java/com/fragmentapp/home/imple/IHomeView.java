package com.fragmentapp.home.imple;

import java.util.List;

/**
 * Created by liuzhen on 2017/11/6.
 */

public interface IHomeView {

    void success(List<String> list);
    void error();

    void loading();
    void loadStop();
}
