package com.fragmentapp.base;

/**
 * Created by liuzhen on 2017/11/6.
 */

public class BasePresenter implements IPresenter{

    @Override
    public <T> T cover(Object t){
        return (T)t;
    }

}
