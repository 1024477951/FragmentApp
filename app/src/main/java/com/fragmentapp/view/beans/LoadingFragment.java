package com.fragmentapp.view.beans;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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

public class LoadingFragment extends BaseDialogFragment {

    @BindView(R.id.tv_val)
    TextView tv_val;
    @BindView(R.id.beans)
    BeansView beans;

    public static LoadingFragment newInstance() {
        LoadingFragment fragment = new LoadingFragment();
        return fragment;
    }
    public static LoadingFragment newInstance(String val) {
        LoadingFragment fragment = new LoadingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("val",val);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_loading;
    }

    @Override
    protected void init() {
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            String val = bundle.getString("val");
            tv_val.setText(val);
        }
        beans.startAnim();
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
    public void dismiss() {
        super.dismiss();

    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            Window window = getDialog().getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.CENTER;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;

            window.setAttributes(params);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        beans.stopAnim();
    }
}
