package com.fragmentapp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.fragmentapp.R;
import com.fragmentapp.base.BaseDialogFragment;
import com.fragmentapp.dynamic.adapter.EmojiListAdapter;
import com.fragmentapp.dynamic.adapter.KeyboardImgAdapter;
import com.fragmentapp.view.bounce.LinIndicate;

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
    @BindView(R.id.layout_emoji)
    View layout_emoji;
    @BindView(R.id.view_indicate)
    LinIndicate view_indicate;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private EmojiListAdapter emojiListAdapter;

    @BindView(R.id.rvImgList)
    RecyclerView rvImgList;
    private KeyboardImgAdapter imgAdapter;

    @BindView(R.id.et_comment)
    EditText et_comment;
    private boolean isClose = false;
    private int keyY = 0;
    private int keyHeight;

    public static KeyboardDialog newInstance() {
        KeyboardDialog fragment = new KeyboardDialog();
        return fragment;
    }
    public static KeyboardDialog newInstance(int val) {
        KeyboardDialog fragment = new KeyboardDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("keyHeight",val);
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
        et_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyY = root.getBottom();
            }
        });
        view_indicate.load(4);
//        setKeyHeight(1000);

        viewPager.setOffscreenPageLimit(1);
        emojiListAdapter = new EmojiListAdapter(getChildFragmentManager(),1);
        viewPager.setAdapter(emojiListAdapter);
    }

    public void reloadEmoji(boolean isOpen){
        if (layout_emoji != null) {
            if (isOpen) {
                if (layout_emoji.getVisibility() == View.GONE)
                    layout_emoji.setVisibility(View.VISIBLE);
            } else {
                if (layout_emoji.getVisibility() == View.VISIBLE)
                    layout_emoji.setVisibility(View.GONE);
            }
        }
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
            if(keyY > 0){
                isClose = false;
            }else {
                isClose = true;
            }
//            Logger.e("getBottom "+root.getBottom()+" getY "+root.getY()+" keyY "+keyY+" isClose "+isClose);
            if (isClose == false){
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (getCurrentFocus() != null) {//是否有获取焦点的view
                        if (getCurrentFocus().getWindowToken() != null) {
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            keyY = 0;//重置关闭逻辑
                        }
                    }
                }
//                return true;
                return super.onTouchEvent(event);
            }else{
                return super.onTouchEvent(event);
            }
        }
    }

    public void setKeyHeight(int keyHeight) {
        this.keyHeight = keyHeight;
        if (layout_emoji != null && keyHeight > 0) {
            layout_emoji.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1000));
        }
    }
}
