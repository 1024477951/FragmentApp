package com.fragmentapp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.EditText;

import com.blankj.utilcode.util.KeyboardUtils;
import com.fragmentapp.R;
import com.fragmentapp.base.BaseDialogFragment;
import com.fragmentapp.dynamic.adapter.EmojiListAdapter;
import com.fragmentapp.dynamic.adapter.KeyboardImgAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liuzhen on 2017/11/17.
 */

public class KeyboardDialog extends BaseDialogFragment implements KeyboardUtils.OnSoftInputChangedListener {

    @BindView(R.id.root)
    View root;
    @BindView(R.id.layout_emoji)
    View layout_emoji;
//    @BindView(R.id.view_indicate)
//    LinIndicate view_indicate;
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
//        KeyboardUtils.registerSoftInputChangedListener(getActivity(),this);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
        }
        KeyboardUtils.showSoftInput(getActivity());
        layout_emoji.setVisibility(View.GONE);
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
                reloadEmoji(false);
            }
        });
//        view_indicate.load(4);
//        setKeyHeight(1000);

        viewPager.setOffscreenPageLimit(1);
        emojiListAdapter = new EmojiListAdapter(getChildFragmentManager(),1);
        viewPager.setAdapter(emojiListAdapter);
        emojiListAdapter.setEmojiCallBack(new EmojiListAdapter.CallBack() {
            @Override
            public void click(String emojiKey) {
                if (emojiKey != null) {
                    et_comment.setText(emojiKey);
                }
            }
        });
    }

    public void reloadEmoji(boolean isOpen){
        if (layout_emoji != null) {
            if (isOpen) {
                if (layout_emoji.getVisibility() == View.GONE) {
                    layout_emoji.setVisibility(View.VISIBLE);
                    if (getActivity() != null) {
                        KeyboardUtils.hideSoftInput(getActivity());
                    }
                }
            } else {
                if (layout_emoji.getVisibility() == View.VISIBLE) {
                    layout_emoji.setVisibility(View.GONE);
                    if (getActivity() != null) {
                        KeyboardUtils.showSoftInput(getActivity());
                    }
                }
            }
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new MyDialog(getContext(), getTheme());
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

    @OnClick({R.id.cb_emoji,R.id.cb_img,R.id.iv_full})
    public void click(View view){
        switch (view.getId()){
            case R.id.cb_emoji:
                reloadEmoji(true);
                break;
            case R.id.cb_img:
                reloadEmoji(true);
                break;
            case R.id.iv_full:

                Window window = getDialog().getWindow();
                WindowManager.LayoutParams params = window.getAttributes();
                params.gravity = Gravity.BOTTOM;
                if (params.height == WindowManager.LayoutParams.MATCH_PARENT){
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                }else{
                    params.height = WindowManager.LayoutParams.MATCH_PARENT;
                }
                params.width = WindowManager.LayoutParams.MATCH_PARENT;

                window.setAttributes(params);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSoftInputChanged(int height) {

    }

    class MyDialog extends AppCompatDialog {

        public MyDialog(Context context, int theme) {
            super(context,theme);
        }

        @Override
        public boolean onTouchEvent(@NonNull MotionEvent event) {
            return super.onTouchEvent(event);
        }
    }

    public void setKeyHeight(int keyHeight) {
        this.keyHeight = keyHeight;
        if (layout_emoji != null && keyHeight > 0) {
            layout_emoji.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1000));
        }
    }

}
