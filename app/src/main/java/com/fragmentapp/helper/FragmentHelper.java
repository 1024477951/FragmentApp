package com.fragmentapp.helper;

import android.os.Bundle;
import android.util.SparseArray;

import com.fragmentapp.base.LazyFragment;
import com.fragmentapp.contacts.fragment.ContactsFragment;
import com.fragmentapp.home.fragment.HomeFragment;

import java.lang.ref.SoftReference;

/**
 * Created by liuzhen on 2017/11/10.
 */

public class FragmentHelper {

    /**
     * 稀疏数组,是Android内部特有的api,标准的jdk是没有这个类的.在Android内部用来替代HashMap<Integer,E>这种形式,使用SparseArray更加节省内存空间的使用,
     * SparseArray也是以key和value对数据进行保存,并且key不需要封装成对象类型,最大的优势就是内存消耗小
     * */
    private static SparseArray<SoftReference<LazyFragment>> arr = new SparseArray<SoftReference<LazyFragment>>();

    public static LazyFragment getFragment(int pos) {
        LazyFragment fragment = null;
        if (null != arr.get(pos))
            fragment = arr.get(pos).get();
        if (null == fragment) {
            switch (pos) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new ContactsFragment();
                    break;
                case 2:
                    fragment = new HomeFragment();
                    break;
                case 3:
                    fragment = new HomeFragment();
                    break;

                default:
                    break;
            }
            Bundle bundle = new Bundle();
            fragment.setArguments(bundle);
            arr.put(pos, new SoftReference<LazyFragment>(fragment));
        }
        return fragment;
    }

    public static int getCount(){
        return 4;
    }

}
