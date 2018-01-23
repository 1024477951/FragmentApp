package com.fragmentapp.view.progress;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.fragmentapp.R;

/**
 * Created by liuzhen on 2018/1/8.
 */

public class Loadding extends ProgressDialog {

    public Loadding(Context context) {
        this(context,R.style.CustomProgressDialog);
    }

    public Loadding(Context context, int theme) {
        super(context, theme);
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loading);
    }

    private void init() {
        setCancelable(true);
        setCanceledOnTouchOutside(false);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
