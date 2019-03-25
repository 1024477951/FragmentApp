package com.fragmentapp.emoji;

import com.fragmentapp.emoji.fragment.EmojiBaseFragment;
import com.fragmentapp.emoji.fragment.EmojiFragment;
import com.fragmentapp.emoji.bean.EmojiItem;
import com.fragmentapp.emoji.manager.StickerCategory;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by liuzhen on 2017/12/14.
 */

public class EmojisListAdapter extends FragmentPagerAdapter {

    private List<StickerCategory> categories;
    private EmojiBaseFragment.CallBack callBack;

    public EmojisListAdapter(FragmentManager fm, List<StickerCategory> categories) {
        super(fm);
        this.categories = categories;
    }

    @Override
    public Fragment getItem(int position) {
        EmojiBaseFragment fragment = EmojisFragmentFactory.getInstance().getFragment(position);
        fragment.setName(categories.get(position).getName());
        fragment.setCallBack(callBack);
        return fragment;
    }

    public void setEmojiCallBack(EmojiBaseFragment.CallBack callBack){
        this.callBack = callBack;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

}