package com.fragmentapp.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
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
    @BindView(R.id.cb_emoji)
    CheckBox cb_emoji;
    @BindView(R.id.cb_img)
    CheckBox cb_img;
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
        KeyboardUtils.registerSoftInputChangedListener(getActivity(),this);
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
//                if (emojiKey != null) {
//                    // 获取当前光标位置,在指定位置上添加表情图片文本
//                    int curPosition = et_comment.getSelectionStart();
//                    StringBuilder sb = new StringBuilder(et_comment.getText().toString());
//                    sb.insert(curPosition, emojiKey);
//
//                    // 特殊文字处理,将表情等转换一下
//                    et_comment.setText(SpanStringUtils.getEmotionContent(sb.toString(), R.dimen.d70_0));
//                    // 将光标设置到新增完表情的右侧
//                    et_comment.setSelection(curPosition + emojiKey.length());
//                    et_comment.requestFocus();
//                }
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (et_comment != null){
            et_comment.setText("");
        }
    }

    public void reloadEmoji(boolean isOpen) {
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
                        cb_img.setChecked(false);
                        cb_emoji.setChecked(false);
                    }
                }
            }
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_comment.getWindowToken(), 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KeyboardUtils.hideSoftInput(getActivity());
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
                if (getActivity() != null) {
                    KeyboardUtils.hideSoftInput(getActivity());
                }
                break;
            case R.id.cb_img:
                cb_emoji.setChecked(true);
                cb_img.setChecked(false);
                hideKeyboard();
                break;
            case R.id.iv_full:
                pushFull();
                break;
        }
    }

    private void pushFull(){
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        if (params.height == WindowManager.LayoutParams.MATCH_PARENT) {
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;

            ConstraintLayout.LayoutParams parm = (ConstraintLayout.LayoutParams) et_comment.getLayoutParams();
            parm.height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
            et_comment.setLayoutParams(parm);
            et_comment.setMaxLines(3);
        } else {
            params.height = WindowManager.LayoutParams.MATCH_PARENT;

            ConstraintLayout.LayoutParams parm = (ConstraintLayout.LayoutParams) et_comment.getLayoutParams();
            parm.height = ConstraintLayout.LayoutParams.MATCH_PARENT;
            et_comment.setLayoutParams(parm);
            et_comment.setMaxLines(Integer.MAX_VALUE);
        }
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        KeyboardUtils.unregisterSoftInputChangedListener(getActivity());
    }

    @Override
    public void onSoftInputChanged(int height) {
        if (height > 0) {
            reloadEmoji(false);
        } else {
            reloadEmoji(true);
        }
    }

    public void setKeyHeight(int keyHeight) {
        this.keyHeight = keyHeight;
        if (layout_emoji != null && keyHeight > 0) {
            layout_emoji.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1000));
        }
    }

}
