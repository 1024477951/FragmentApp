package com.fragmentapp.view.sticky;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.fragmentapp.R;
import com.fragmentapp.helper.DensityUtil;


public abstract class BaseDecoration extends RecyclerView.ItemDecoration {

    private int height;
    private Paint paint;

    public BaseDecoration(Context context){
        paint = new Paint();
        paint.setColor(context.getResources().getColor(R.color.color_8b90af));
        height = DensityUtil.dp2px(context,1);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = height;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() + left;
//        Log.e("tag","left "+left+" right "+right);
        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + height;
            c.drawRect(left+view.getLeft(), top, view.getRight(), bottom, paint);
        }
    }
}
