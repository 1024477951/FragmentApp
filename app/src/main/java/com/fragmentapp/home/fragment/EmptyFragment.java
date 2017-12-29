package com.fragmentapp.home.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.fragmentapp.R;
import com.fragmentapp.base.BaseDialogFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liuzhen on 2017/11/17.
 */

public class EmptyFragment extends BaseDialogFragment {

    @BindView(R.id.tv_val)
    TextView tv_val;

    public static EmptyFragment newInstance(Bundle bundle) {
        EmptyFragment fragment = new EmptyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    public static EmptyFragment newInstance(String val) {
        EmptyFragment fragment = new EmptyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("val",val);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_error;
    }

    @Override
    protected void init() {
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            String val = bundle.getString("val");
            tv_val.setText(val);
        }
    }

    @OnClick({R.id.root})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.root:
                break;
            default:
                break;
        }
        dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            Window window = getDialog().getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.CENTER;
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.MATCH_PARENT;

            window.setAttributes(params);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }

    }

}
