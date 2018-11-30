package com.fragmentapp.dynamic.adapter;

import android.os.Bundle;
import android.util.SparseArray;

import com.fragmentapp.base.BaseFragment;
import com.fragmentapp.dynamic.fragment.EmojiFragment;

import java.lang.ref.SoftReference;

/**
 * Created by liuzhen on 2017/12/14.
 */

public class EmojiFragmentFactory {

    private EmojiFragmentFactory(){}

    private static class Instance{
        static EmojiFragmentFactory instance = new EmojiFragmentFactory();
    }

    public static EmojiFragmentFactory getInstance(){
        return Instance.instance;
    }

    private SparseArray<SoftReference<BaseFragment>> arr = new SparseArray<SoftReference<BaseFragment>>();

    public BaseFragment getFragment(int pos) {
        BaseFragment fragment = null;
        if (null != arr.get(pos))
            fragment = arr.get(pos).get();
        if (null == fragment) {
            Bundle bundle = new Bundle();
            bundle.putInt("id",pos);
            switch (pos) {
                case 0:
                    fragment = EmojiFragment.newInstance(bundle);
                    break;
            }
            arr.put(pos, new SoftReference<BaseFragment>(fragment));
        }
        return fragment;
    }

}
