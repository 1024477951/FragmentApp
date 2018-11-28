package com.fragmentapp.base;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fragmentapp.R;
import com.fragmentapp.Tabs;
import com.fragmentapp.view.dialog.LoadingDialog;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liuzhen on 2018/9/28.
 */

public abstract class IMFragment extends BaseFragment {

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

    private boolean isLoad = false;//是否加载完

//    protected EmptyLayout emptyLayout;
    //    protected Loadding loadding;
    private LoadingDialog loadingDialog;

    protected Unbinder unbinder;

    private ImmersionBar mImmersionBar;

    private Tabs factory;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_error;
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
        }
    }

    /**可见时调用*/
    protected void onVisible(){
        if(isVisible == false || isLoad || loadRealLayout() == false) {
            return;
        }
//        emptyLayout = new EmptyLayout(getActivity());//初始化空页面布局
        loadingDialog = LoadingDialog.newInstance();

        mImmersionBar = ImmersionBar.with(this)
                .keyboardEnable(true)
                .navigationBarWithKitkatEnable(false);
        mImmersionBar.init();

        unbinder = ButterKnife.bind(this, contentView);
        init();
        isLoad = true;//加载完后更改状态，只限定加载一次
    }

    public void attachTabData(Tabs factory) {
        this.factory = factory;
    }

    private boolean loadRealLayout() {
        ViewGroup root = (ViewGroup) getView();
        if (root != null) {
            root.removeAllViewsInLayout();
            View.inflate(root.getContext(), factory.layoutId, root);
        }
        return root != null;
    }

    protected void showDialog(){
        if (loadingDialog.isVisible() == false){
            loadingDialog.show(getFragmentManager(),TAG);
        }
    }
    protected void dismissDialog(){
        if (loadingDialog.isVisible() == true){
            loadingDialog.dismiss();
        }
    }

    protected void setTitleText(String title){
        tv_title.setText(title);
    }

    protected void setTitleLeftText(String title){
        tv_left_title.setVisibility(View.VISIBLE);
        tv_left_title.setText(title);

        tv_read.setVisibility(View.GONE);
        circle_read.setVisibility(View.GONE);
    }

    protected void setTitleRightText(String title){
        tv_right_title.setVisibility(View.VISIBLE);
        tv_right_title.setText(title);

        menu.setVisibility(View.GONE);
        img_menu_icon.setVisibility(View.GONE);
    }

    protected void setTitleRightText(String title, View.OnClickListener listener){
        tv_right_title.setVisibility(View.VISIBLE);
        tv_right_title.setText(title);

        menu.setVisibility(View.GONE);
        img_menu_icon.setVisibility(View.GONE);

        if (listener != null){
            tv_right_title.setOnClickListener(listener);
        }
    }

    protected void isVisibleLeftBack(boolean b){
        if (b) {
            img_left_icon.setVisibility(View.VISIBLE);
        }else{
            img_left_icon.setVisibility(View.GONE);
        }
    }

    protected void setTitleRightMenu(int res){
        img_menu_icon.setImageResource(res);
        img_menu_icon.setVisibility(View.VISIBLE);

        tv_right_title.setVisibility(View.GONE);
    }

    protected void setTitleText(String title,boolean isGoneTriangle){
        tv_title.setText(title);
    }

    protected void setTitleReadNum(String num){
        tv_read.setText(num);
        tv_read.setVisibility(View.VISIBLE);
        circle_read.setVisibility(View.VISIBLE);

        tv_left_title.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (emptyLayout != null)
//            emptyLayout.clear();
//        emptyLayout = null;
        loadingDialog = null;
        if (mImmersionBar != null)
            mImmersionBar.destroy();
        if (unbinder != null)
            unbinder.unbind();
        unbinder = null;
    }

}
