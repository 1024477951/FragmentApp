package com.fragmentapp.view.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.fragmentapp.R;
import com.fragmentapp.base.BaseDialogFragment;

import butterknife.BindView;
import butterknife.OnClick;


public class AlertAlphaDialogFragment extends BaseDialogFragment {

    @BindView(R.id.iv_sound)
    ImageView iv_sound;
    @BindView(R.id.tv_content)
    TextView tv_content;

    public static AlertAlphaDialogFragment newInstance(Bundle bundle) {
        AlertAlphaDialogFragment fragment = new AlertAlphaDialogFragment();
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
            params.gravity = Gravity.CENTER;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;

            window.setAttributes(params);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_alpha_dialog;
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

        Bundle bundle = getArguments();
        if (bundle != null){
            int res = bundle.getInt("res");
            iv_sound.setImageResource(res);
            String content = bundle.getString("content");
            tv_content.setText(content);
        }
    }

    @OnClick({R.id.root})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.root:

                break;
        }
        dismiss();
    }
}
