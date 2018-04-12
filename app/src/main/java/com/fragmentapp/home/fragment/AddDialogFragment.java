package com.fragmentapp.home.fragment;

import android.content.DialogInterface;
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
import com.fragmentapp.home.FragmentFactory;

import butterknife.OnClick;


public class AddDialogFragment extends BaseDialogFragment {

    public static AddDialogFragment newInstance(Bundle bundle) {
        AddDialogFragment fragment = new AddDialogFragment();
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
        return R.layout.layout_add_dialog;
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
                FragmentFactory.newInstance(getContext(),FragmentFactory.Zxing);
                break;
            case R.id.group:

                break;
        }
        dismiss();
    }
}
