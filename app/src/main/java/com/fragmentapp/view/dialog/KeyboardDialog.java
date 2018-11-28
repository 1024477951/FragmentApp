package com.fragmentapp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.fragmentapp.R;
import com.fragmentapp.base.BaseDialogFragment;
import com.fragmentapp.dynamic.adapter.KeyboardImgAdapter;
import com.fragmentapp.view.beans.BeansView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liuzhen on 2017/11/17.
 */

public class KeyboardDialog extends BaseDialogFragment {

    @BindView(R.id.root)
    View root;

    @BindView(R.id.rvImgList)
    RecyclerView rvImgList;
    private KeyboardImgAdapter imgAdapter;

    @BindView(R.id.et_comment)
    EditText et_comment;
    private boolean isClose = false;

    public static KeyboardDialog newInstance() {
        KeyboardDialog fragment = new KeyboardDialog();
        return fragment;
    }
    public static KeyboardDialog newInstance(String val) {
        KeyboardDialog fragment = new KeyboardDialog();
        Bundle bundle = new Bundle();
        bundle.putString("val",val);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_keyboard;
    }

    @Override
    protected void init() {
        if (getArguments() != null) {
            Bundle bundle = getArguments();

        }
        Logger.e("init y "+root.getBottom());
        imgAdapter = new KeyboardImgAdapter(R.layout.item_dynamic_keyboard_img);
        rvImgList.setAdapter(imgAdapter);
        List<String> items = new ArrayList<>();
        for(int i = 0;i< 3;i++){
            items.add(""+i);
        }
        imgAdapter.setNewData(items);
        et_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new MyDialog(getContext(), getTheme());
    }

    @OnClick({R.id.root})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.root:
                break;
            default:
                break;
        }
    }

    public void onSwitch(FragmentManager manager){
        if (isVisible() == false){
            show(manager,TAG);
        }else{
            dismiss();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(true);
            Window window = getDialog().getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.BOTTOM;
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;

            window.setAttributes(params);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    class MyDialog extends AppCompatDialog {

        public MyDialog(Context context, int theme) {
            super(context,theme);
        }

        @Override
        public boolean onTouchEvent(@NonNull MotionEvent event) {
            if(root != null && root.getBottom() > 0){
                isClose = false;
            }else {
                isClose = true;
            }
            Logger.e("getBottom "+root.getBottom()+" getY "+root.getY()+"");
            if (isClose == false){
                InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return true;
            }else{
                return super.onTouchEvent(event);
            }
        }
    }

}
