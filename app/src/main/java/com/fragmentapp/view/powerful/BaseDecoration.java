package com.fragmentapp.view.powerful;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

/**
 * Created by ${Bryan} on 2017/8/20.
 */

public abstract class BaseDecoration extends RecyclerView.ItemDecoration {
    @ColorInt
    int mGroupBackground = Color.parseColor("#FAFAFA");//group背景色，默认透明
    int mGroupHeight = 80;  //悬浮栏高度
    boolean isAlignLeft = true; //是否靠左边
    @ColorInt
    int mDivideColor = Color.parseColor("#FAFAFA");//分割线颜色，默认灰色
    int mDivideHeight = 0;      //分割线高度

    Paint mDividePaint;

    public BaseDecoration() {
        mDividePaint = new Paint();
        mDividePaint.setColor(mDivideColor);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        String groupId = getGroupName(pos);
        if (groupId == null) return;
        //只有是同一组的第一个才显示悬浮栏
        if (pos == 0 || isFirstInGroup(pos)) {
            outRect.top = mGroupHeight; //为悬浮view预留空间
        } else {
            outRect.top = mDivideHeight; //为分割线预留空间
        }
    }

    /**
     * 判断是不是组中的第一个位置
     * 根据前一个组名，判断当前是否为新的组
     */
    private boolean isFirstInGroup(int pos) {
        if (pos == 0) {
            return true;
        } else {
            String prevGroupId = getGroupName(pos - 1);
            String groupId = getGroupName(pos);
            return !TextUtils.equals(prevGroupId, groupId);
        }
    }

    abstract String getGroupName(int position);



}
