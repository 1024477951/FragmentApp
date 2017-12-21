package com.fragmentapp.helper;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by liuzhen on 2017/12/19.
 */

public class EmptyUtil {

    /**
     * @param empty_view 空页面view
     * @param empty_root 空白页面可点击区域
     * */
    private View empty_view,empty_root;
    /**
     * @param empty_container 父容器（用来显示空页面的容器）
     * */
    private ViewGroup empty_container;
    private ImageView img_icon;
    private TextView tv_val;
    private CallBack callBack;

    /**加载空白页视图和容器布局*/
    public void loadEmptyView(View empty_view){
        this.empty_view = empty_view;

        empty_container = new FrameLayout(empty_view.getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        empty_container.setLayoutParams(params);

        empty_container.addView(empty_view);
        empty_container.setVisibility(View.VISIBLE);

//        img_icon = findViewById(R.id.img_icon);
//        tv_val = findViewById(R.id.tv_val);
//        empty_root = findViewById(R.id.empty_root);
    }

    public void removeAll(){
        empty_view = null;
        callBack = null;
        empty_container = null;
    }

    private <T extends View> T findViewById(int id){
        return empty_view.findViewById(id);
    }

    public EmptyUtil setEmptyVal(int resID,String val){
        img_icon.setImageResource(resID);
        tv_val.setText(val);
        return this;
    }

    public EmptyUtil setCallBack(final CallBack callBack){
        this.callBack = callBack;
        empty_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                empty_container.setVisibility(View.GONE);
                callBack.onClick();
            }
        });
        return this;
    }

    public View getEmptyView(){
        return empty_view;
    }

    public interface CallBack{
        void onClick();
    }

}
