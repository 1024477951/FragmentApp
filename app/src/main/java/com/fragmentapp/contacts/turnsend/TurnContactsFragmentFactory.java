package com.fragmentapp.contacts.turnsend;


import android.util.SparseArray;

import com.fragmentapp.base.LazyFragment;
import com.fragmentapp.contacts.partgroup.PartFragment;
import com.fragmentapp.contacts.turnsend.turncontacts.ImGroupFragment;
import com.fragmentapp.contacts.turnsend.turncontacts.JobFragment;
import com.fragmentapp.contacts.turnsend.turncontacts.TeanFragment;

import java.lang.ref.SoftReference;

public class TurnContactsFragmentFactory {
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
