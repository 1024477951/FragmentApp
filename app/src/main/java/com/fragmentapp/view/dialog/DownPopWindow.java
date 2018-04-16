package com.fragmentapp.view.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.fragmentapp.R;

/**
 * Created by liuzhen on 2018/1/9.
 */

public class DownPopWindow extends PopupWindow{

    private Context mContext;
    private CallBack callBack;
    private View view;

    private View cancel,save;


    public DownPopWindow(Context mContext, final CallBack callBack) {
        this.mContext = mContext;
        this.callBack = callBack;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.layout_down_pop, null);

        cancel = view.findViewById(R.id.cancel);
        save = view.findViewById(R.id.save);
        // 取消按钮
        cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // 销毁弹出框
                dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                callBack.click(v);
                dismiss();
            }
        });


        // 设置外部可点击
        this.setOutsideTouchable(true);
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.pop_layout).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


    /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);
    }

    public void showPopFormBottom(View view) {
//        设置Popupwindow显示位置（从底部弹出）
        showAtLocation(view, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public interface CallBack{
        void click(View v);
    }

}
