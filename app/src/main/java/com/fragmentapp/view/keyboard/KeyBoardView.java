package com.fragmentapp.view.keyboard;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fragmentapp.R;
import com.fragmentapp.emoji.EmojisListAdapter;
import com.fragmentapp.emoji.fragment.EmojiBaseFragment;
import com.fragmentapp.emoji.manager.StickerCategory;
import com.fragmentapp.emoji.manager.StickerManager;

import java.util.List;

/**
 * Created by liuzhen on 2019/2/20.
 */

public class KeyBoardView extends LinearLayout implements View.OnClickListener{

    private View root,iv_emoji;

    private LinearLayout layout_menu;
    private ConstraintLayout.LayoutParams menuParams = null;
    private int defHeight;

    private ViewPager viewPager;
    private EmojisListAdapter emojisListAdapter;

    private EditText et_content;

    public KeyBoardView(Context context) {
        this(context,null);
    }

    public KeyBoardView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public KeyBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        root = LayoutInflater.from(getContext()).inflate(R.layout.layout_keyboard,this,false);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        root.setLayoutParams(params);

        root.measure(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);
//        defHeight = root.getMeasuredHeight();

        iv_emoji = root.findViewById(R.id.iv_emoji);
        viewPager = root.findViewById(R.id.viewPager);
        layout_menu = root.findViewById(R.id.layout_menu);
        viewPager.setVisibility(GONE);
        menuParams = (ConstraintLayout.LayoutParams) layout_menu.getLayoutParams();

        iv_emoji.setOnClickListener(this);
    }

    public void setKeyBoardView(FragmentManager fragmentManager){
        addView(root);
        // 贴图
        List<StickerCategory> categories = StickerManager.getInstance().getCategories();//只添加了贴图，还没有添加默认表情

        viewPager.setOffscreenPageLimit(categories.size());
        emojisListAdapter = new EmojisListAdapter(fragmentManager,categories);
        viewPager.setAdapter(emojisListAdapter);
        emojisListAdapter.setEmojiCallBack(new EmojiBaseFragment.CallBack() {
            @Override
            public void click(String path, String name) {
                if (name.equals("emoji")) {
                    Editable mEditable = et_content.getText();
                    if (path.equals("/DEL")) {
                        et_content.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                    } else {
                        int start = et_content.getSelectionStart();
                        int end = et_content.getSelectionEnd();
                        start = (start < 0 ? 0 : start);
                        end = (start < 0 ? 0 : end);
                        mEditable.replace(start, end, path);
                    }
                }
            }
        });
    }

    public void updateHeight(int height){
        menuParams.height = height + defHeight;
        layout_menu.setLayoutParams(menuParams);
        if (height > 0){
            viewPager.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_emoji:
                viewPager.setVisibility(VISIBLE);
                break;
        }
    }
}
