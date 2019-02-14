package com.fragmentapp.dynamic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fragmentapp.dynamic.fragment.EmojiFragment;
import com.fragmentapp.emoji.EmojiItem;
import com.fragmentapp.emoji.StickerCategory;
import com.fragmentapp.emoji.StickerItem;

import java.util.List;

/**
 * Created by liuzhen on 2017/12/14.
 */

public class EmojiListAdapter extends FragmentPagerAdapter {

    public EmojiListAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        EmojiFragment fragment = EmojiFragmentFactory.getInstance().getFragment(position);
//        fragment.setName(list.get(position).getName());
        return fragment;
    }

    public void setEmojiCallBack(CallBack callBack){
        ((EmojiFragment)getItem(0)).setCallBack(callBack);
    }

    @Override
    public int getCount() {
        return 1;
    }

    public interface CallBack{
        void click(EmojiItem item, int position);
    }

}