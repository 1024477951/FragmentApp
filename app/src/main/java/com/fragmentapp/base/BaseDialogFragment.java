package com.fragmentapp.base;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by liuzhen on 2017/11/20.
 */

public abstract class BaseDialogFragment extends AppCompatDialogFragment {

    protected View contentView;
    protected String TAG = getClass().getSimpleName();

    protected abstract int getLayoutId();
    protected abstract void init();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getDialog() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (contentView == null) {
            contentView = inflater.inflate(getLayoutId(), container);
            ButterKnife.bind(this, contentView);
        } else {
            ViewGroup parent = (ViewGroup) contentView.getParent();
            if (parent != null) {
                parent.removeView(contentView);
            }
        }
        setCancelable(false);
        init();
        return contentView;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}
