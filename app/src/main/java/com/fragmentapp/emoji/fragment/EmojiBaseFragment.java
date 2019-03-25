package com.fragmentapp.emoji.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fragmentapp.R;
import com.fragmentapp.base.BaseFragment;
import com.fragmentapp.base.LazyFragment;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liuzhen on 2019/2/14.
 * 表情 base类
 */

public abstract class EmojiBaseFragment extends BaseFragment {

    //-------toolbar-------
    /** left */
    @Nullable
    @BindView(R.id.fl_left)
    public View fl_left;
    @Nullable @BindView(R.id.tv_left_title)
    public TextView tv_left_title;
    @Nullable @BindView(R.id.tv_read)
    public TextView tv_read;
    @Nullable @BindView(R.id.circle_read)
    public View circle_read;
    @Nullable @BindView(R.id.img_left_icon)
    public ImageView img_left_icon;

    /** center */
    @Nullable @BindView(R.id.tv_title)
    public TextView tv_title;

    /** right */
    @Nullable @BindView(R.id.tv_right_title)
    public TextView tv_right_title;
    @Nullable @BindView(R.id.menu)
    public FrameLayout menu;
    @Nullable @BindView(R.id.img_menu_icon)
    public ImageView img_menu_icon;
    //-------toolbar-------

    protected Unbinder unbinder;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, contentView);
        init();
    }

    protected CallBack callBack;

    protected String name;

    public void setName(String name) {
        this.name = name;
    }

    public void setCallBack(CallBack callBack){
        this.callBack = callBack;
    }

    public interface CallBack{
        void click(String path, String name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
        unbinder = null;
    }
}
