package com.fragmentapp.view.dialog;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;

import com.flyco.animation.FlipEnter.FlipVerticalSwingEnter;
import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.fragmentapp.R;

import butterknife.ButterKnife;

public class ShareBottomDialog extends BottomBaseDialog<ShareBottomDialog> {

    public ShareBottomDialog(Context context, View animateView) {
        super(context, animateView);
    }

    public ShareBottomDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        showAnim(new FlipVerticalSwingEnter());
        dismissAnim(null);
        View inflate = View.inflate(mContext, R.layout.dialog_keyboard, null);
        ButterKnife.bind(this, inflate);
        setCanceledOnTouchOutside(false);
        return inflate;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void setUiBeforShow() {


    }
}
