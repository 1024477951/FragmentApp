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
import android.widget.TextView;

import com.fragmentapp.R;
import com.fragmentapp.base.BaseDialogFragment;

import butterknife.BindView;
import butterknife.OnClick;


public class AlertDialogFragment extends BaseDialogFragment {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_content)
    TextView tv_content;

    public static AlertDialogFragment newInstance(Bundle bundle) {
        AlertDialogFragment fragment = new AlertDialogFragment();
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
        return R.layout.layout_alert_dialog;
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
            String title = bundle.getString("title");
            tv_title.setText(title);
            String content = bundle.getString("content");
            tv_content.setText(content);
        }
    }

    @OnClick({R.id.confirm,R.id.root})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.confirm:

                break;
            case R.id.root:

                break;
        }
        dismiss();
    }
}
