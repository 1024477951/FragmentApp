package com.fragmentapp.view.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.KeyboardUtils;
import com.fragmentapp.R;
import com.fragmentapp.base.BaseDialogFragment;
import com.fragmentapp.dynamic.adapter.EmojiListAdapter;
import com.fragmentapp.dynamic.adapter.KeyboardImgAdapter;
import com.fragmentapp.emoji.EmojiItem;
import com.fragmentapp.emoji.EmojiManager;
import com.fragmentapp.emoji.StickerCategory;
import com.fragmentapp.emoji.StickerItem;
import com.fragmentapp.emoji.StickerManager;
import com.fragmentapp.selector.PhotoSelectUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liuzhen on 2017/11/17.
 */

public class KeyboardDialog extends BaseDialogFragment implements KeyboardUtils.OnSoftInputChangedListener {

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
    private int keyHeight;

    private boolean isFirst = true;

    public static KeyboardDialog newInstance() {
        KeyboardDialog fragment = new KeyboardDialog();
        return fragment;
    }

    public static KeyboardDialog newInstance(int val) {
        KeyboardDialog fragment = new KeyboardDialog();
        Bundle bundle = new Bundle();
//        bundle.putInt("keyHeight",val);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_keyboard;
    }

    @Override
    protected void init() {
        KeyboardUtils.registerSoftInputChangedListener(getActivity(), this);
        KeyboardUtils.showSoftInput(getActivity());
        layout_emoji.setVisibility(View.GONE);
        imgAdapter = new KeyboardImgAdapter(R.layout.item_dynamic_keyboard_img);
        rvImgList.setAdapter(imgAdapter);
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            items.add("" + i);
        }
        imgAdapter.setNewData(items);
        et_comment.addTextChangedListener(new TextWatcher() {

            private int start;
            private int after;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                this.start = start;
                this.after = after;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                EmojiManager.replaceEmoticons(getActivity(), s, start, after);
            }
        });
        et_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadEmoji(false);
            }
        });
//        view_indicate.load(4);
//        setKeyHeight(1000);

        // 贴图
//        List<StickerCategory> categories = StickerManager.getInstance().getCategories();//只添加了贴图，还没有添加默认表情

        viewPager.setOffscreenPageLimit(1);
        emojiListAdapter = new EmojiListAdapter(getChildFragmentManager());
        viewPager.setAdapter(emojiListAdapter);
        emojiListAdapter.setEmojiCallBack(new EmojiListAdapter.CallBack() {
            @Override
            public void click(EmojiItem item, int position) {
                Editable mEditable = et_comment.getText();
                if (item.text.equals("/DEL")) {
                    et_comment.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                } else {
                    int start = et_comment.getSelectionStart();
                    int end = et_comment.getSelectionEnd();
                    start = (start < 0 ? 0 : start);
                    end = (start < 0 ? 0 : end);
                    mEditable.replace(start, end, item.text);
                }
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (et_comment != null) {
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

    public void onSwitch(FragmentManager manager) {
        if (isVisible() == false) {
            show(manager, TAG);
        } else {
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

    @OnClick({R.id.cb_emoji, R.id.cb_img, R.id.iv_full, R.id.iv_emoji_del, R.id.tv_send})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                hideKeyboard();
                dismiss();
                break;
            case R.id.iv_emoji_del:
                if (et_comment != null) {
                    et_comment.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                }
                break;
            case R.id.cb_emoji:
                cb_emoji.setChecked(true);
                cb_img.setChecked(false);
                hideKeyboard();
                break;
            case R.id.cb_img:
                cb_emoji.setChecked(false);
                cb_img.setChecked(true);
                PhotoSelectUtils.getInstance().openNum(this, 3);
                break;
            case R.id.iv_full:
                pushFull();
                break;
        }
    }

    private void pushFull() {
        LinearLayout.LayoutParams parm = (LinearLayout.LayoutParams) et_comment.getLayoutParams();
        if (parm.height != WindowManager.LayoutParams.WRAP_CONTENT) {
            et_comment.setMaxLines(3);
            parm.height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        } else {
            et_comment.setMaxLines(Integer.MAX_VALUE);
            parm.height = keyHeight;
        }
        et_comment.setLayoutParams(parm);
    }

    private void setFullHeight(int height) {
        LinearLayout.LayoutParams parm = (LinearLayout.LayoutParams) et_comment.getLayoutParams();
        if (parm.height != WindowManager.LayoutParams.WRAP_CONTENT) {
            parm.height = height;
        } else {
            parm.height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        }
        et_comment.setLayoutParams(parm);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        KeyboardUtils.unregisterSoftInputChangedListener(getActivity());
    }

    @Override
    public void onSoftInputChanged(int height) {
        if (height > 0) {
            if (isFirst) {
                keyHeight = height;
                isFirst = false;
            }
            reloadEmoji(false);
        } else {
            reloadEmoji(true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {
            if (data != null) {
                List<LocalMedia> photos = PictureSelector.obtainMultipleResult(data);
                List<String> items = new ArrayList<>();
                for (LocalMedia media : photos) {
                    items.add(media.getPath());
                }
                if (imgAdapter != null) {
                    if (imgAdapter.getData().size() > 0) {
                        imgAdapter.addData(items);
                    } else {
                        imgAdapter.setNewData(items);
                    }
                }
            }

        }
    }

}
