package com.fragmentapp.view.sticky;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fragmentapp.helper.DensityUtil;

/**
 * Created by liuzhen on 2017/12/15.
 */

public class PaddingDecoration extends RecyclerView.ItemDecoration{

    private int dividerHeight;


    public PaddingDecoration(Context context, int px) {
        dividerHeight = DensityUtil.px2dp(context,px);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = dividerHeight;//类似加了一个bottom padding
    }

}
