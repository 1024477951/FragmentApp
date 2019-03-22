package com.fragmentapp.view.refresh;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.fragmentapp.R;
import com.fragmentapp.helper.DensityUtil;

/**
 * Created by liuzhen on 2017/11/22.
 */

public class TextHeadView extends View implements IHeadView{

    private final String TAG = "tag";
    private Paint mPaint;
    private String val;

    public TextHeadView(@NonNull Context context) {
        this(context,null,0);
    }

    public TextHeadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TextHeadView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init(){
        //画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.color_a9a05c));
        mPaint.setTextSize(DensityUtil.dp2px(getContext(),13));
    }

    /**
     * 绘制
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (val == null)
            return;
        int width = getHeight()/2;
        canvas.drawText(val,width/2,width,mPaint);
    }

    public void setText(String val){
        if (val == null)
            return;
        this.val = val;
        invalidate();
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void startAnim() {

    }

    @Override
    public void stopAnim() {

    }
}
