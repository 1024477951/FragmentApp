package com.fragmentapp.emoji;

import android.os.Bundle;
import android.util.SparseArray;

import com.fragmentapp.emoji.fragment.EmojiBaseFragment;
import com.fragmentapp.emoji.fragment.EmojiFragment;
import com.fragmentapp.emoji.fragment.StickerFragment;

import java.lang.ref.SoftReference;

/**
 * Created by liuzhen on 2017/12/14.
 */

public class EmojisFragmentFactory {

    private EmojisFragmentFactory(){}

    private static class Instance{
        static EmojisFragmentFactory instance = new EmojisFragmentFactory();
    }

    public static EmojisFragmentFactory getInstance(){
        return Instance.instance;
    }

    private SparseArray<SoftReference<EmojiBaseFragment>> arr = new SparseArray<SoftReference<EmojiBaseFragment>>();

    public EmojiBaseFragment getFragment(int pos) {
        EmojiBaseFragment fragment = null;
        if (null != arr.get(pos))
            fragment = arr.get(pos).get();
        if (null == fragment) {
            Bundle bundle = new Bundle();
            bundle.putInt("id",pos);
            switch (pos) {
                case 0:
                    fragment = EmojiFragment.newInstance(bundle);
                    break;
                default:
                    fragment = StickerFragment.newInstance(bundle);
                    break;
            }
            arr.put(pos, new SoftReference<EmojiBaseFragment>(fragment));
        }
        return fragment;
    }

}
