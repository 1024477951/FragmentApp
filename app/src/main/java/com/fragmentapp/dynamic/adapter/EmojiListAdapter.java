package com.fragmentapp.dynamic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fragmentapp.dynamic.fragment.EmojiFragment;
import com.fragmentapp.emoji.StickerCategory;
import com.fragmentapp.emoji.StickerItem;

import java.util.List;

/**
 * Created by liuzhen on 2017/12/14.
 */

public class EmojiListAdapter extends FragmentPagerAdapter {

    private List<StickerCategory> list;

    public EmojiListAdapter(FragmentManager fm, List<StickerCategory> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        EmojiFragment fragment = EmojiFragmentFactory.getInstance().getFragment(position);
        fragment.setName(list.get(position).getName());
        return fragment;
    }

    public void setEmojiCallBack(CallBack callBack){
        ((EmojiFragment)getItem(0)).setCallBack(callBack);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public interface CallBack{
        void click(StickerItem item,int position);
    }

}