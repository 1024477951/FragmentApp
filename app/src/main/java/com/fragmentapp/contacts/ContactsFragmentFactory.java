package com.fragmentapp.contacts;

import android.util.SparseArray;

import com.fragmentapp.base.LazyFragment;
import com.fragmentapp.contacts.imgroup.ImGroupFragment;
import com.fragmentapp.contacts.jobgroup.JobFragment;
import com.fragmentapp.contacts.partgroup.PartFragment;
import com.fragmentapp.contacts.teamgroup.TeanFragment;

import java.lang.ref.SoftReference;

/**
 * Created by Bryan on 2017/8/4.
 */

public class ContactsFragmentFactory {
    private static SparseArray<SoftReference<LazyFragment>> arr = new SparseArray<SoftReference<LazyFragment>>();

    public static LazyFragment getFragment(int pos) {
        LazyFragment fragment = null;
        if (null != arr.get(pos))
            fragment = arr.get(pos).get();
        if (null == fragment) {
            switch (pos) {
                case 0:
                    fragment=new ImGroupFragment();

                    break;
                case 1:
                    fragment = new TeanFragment();

                    break;
                case 2:
                    fragment = new JobFragment();

                    break;
                case 3:
                    fragment = new PartFragment();
                    break;
                default:
                    break;
            }
            arr.put(pos, new SoftReference<LazyFragment>(fragment));
        }
        return fragment;
    }
}
