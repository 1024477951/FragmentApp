package com.fragmentapp.home.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.fragmentapp.R;
import com.fragmentapp.base.BaseDialogFragment;

import butterknife.OnClick;


public class AlertFragment extends BaseDialogFragment {

    public static AlertFragment newInstance(Bundle bundle) {
        AlertFragment fragment = new AlertFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,R.style.ThemeOverlay_AppCompat_Dialog);
        setCancelable(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            Window window = getDialog().getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.TOP|Gravity.RIGHT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;

            window.setAttributes(params);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_alert_zxing;
    }

    @Override
    protected void init() {
        getDialog().setCanceledOnTouchOutside(true);
        setCancelable(true);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.zxing,R.id.group})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.zxing:

                break;
            case R.id.group:

                break;
        }
        dismiss();
    }
}
