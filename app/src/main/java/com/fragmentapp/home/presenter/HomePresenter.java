package com.fragmentapp.home.presenter;

import com.fragmentapp.base.BasePresenter;
import com.fragmentapp.chat.models.DefaultUser;
import com.fragmentapp.chat.models.MyMessage;
import com.fragmentapp.home.bean.ChatBean;
import com.fragmentapp.home.imple.IHomeModel;
import com.fragmentapp.home.imple.IHomeView;
import com.fragmentapp.home.model.HomeModel;
import com.fragmentapp.http.BaseObserver;
import com.fragmentapp.http.BaseResponses;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.jiguang.imui.commons.models.IMessage;

/**
 * Created by liuzhen on 2017/12/20.
 */

public class HomePresenter extends BasePresenter {

    private IHomeView view;

    public HomePresenter(IHomeView view){
        this.view = view;
    }

    public void getArticleList(int page){
        List<ChatBean> list = new ArrayList<>();

        MyMessage message = new MyMessage("有人@我", IMessage.MessageType.EVENT.ordinal());

        for (int i = 0;i<20;i++){
            ChatBean bean = new ChatBean();
            bean.setTop(0);
            bean.setId(i);
            bean.setTime(System.currentTimeMillis()-i);
            if (i == 1) {
                bean.setType(ChatBean.Group);
                bean.setLastMsg(message);
            }
            else if (i == 2)
                bean.setType(ChatBean.File);
            else if (i == 3)
                bean.setType(ChatBean.Meet);
            else if (i == 4)
                bean.setType(ChatBean.Leaves);
            else
                bean.setType(ChatBean.Single);

            list.add(bean);
        }

        view.success(list);
    }



}
