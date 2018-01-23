package com.fragmentapp.helper;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fragmentapp.R;

/**
 * Created by liuzhen on 2017/12/28.
 */

public class EmptyLayout {

    private Context context;
    private FrameLayout root;

    private TextView tv_val;
    private CallBack callBack;

    public EmptyLayout(Context context){
        this.context = context;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;

        root = new FrameLayout(context);
        root.setLayoutParams(params);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_error,null);
        tv_val = view.findViewById(R.id.tv_val);
        root.addView(view);
    }

    public void showEmpty(final ViewGroup group, String val){
        tv_val.setText(val);

        group.removeView(root);
        group.addView(root,root.getLayoutParams());

        status(group,true);

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBack != null)
                    callBack.click();
                status(group,false);
            }
        });
    }

    private void status(ViewGroup group,boolean isShow){
        if (isShow){
            for (int i = 0;i < group.getChildCount();i++) {
                View view = group.getChildAt(i);
                view.setVisibility(View.GONE);
            }
            root.setVisibility(View.VISIBLE);//放在后面才能生效
        }else{
            for (int i = 0;i < group.getChildCount();i++) {
                View view = group.getChildAt(i);
                view.setVisibility(View.VISIBLE);
            }
            root.setVisibility(View.GONE);
        }

    }

    public void setCallBack(CallBack callBack){
        if (this.callBack == null)
            this.callBack = callBack;
    }

    public void clear(){
        context = null;
        root.removeAllViews();
        root = null;
        callBack = null;
    }

    public interface CallBack{
        void click();
    }

}
