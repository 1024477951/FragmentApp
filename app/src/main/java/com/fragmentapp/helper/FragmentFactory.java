package com.fragmentapp.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;

import com.fragmentapp.R;
import com.fragmentapp.base.BaseActivity;
import com.fragmentapp.base.BaseFragment;

import java.util.List;

import butterknife.BindView;

/**
 * Created by liuzhen on 2017/11/17.
 */

public class FragmentFactory extends BaseActivity {

    @BindView(R.id.fragments)
    FrameLayout fragments;

    private Bundle mBundle;

    public static final int MeetZxing = 0;
    public static final int TurnNewList = 1;
    public static final int TurnContacts = 2;

    public static void newInstance(Context context, int key) {
        Intent intent = new Intent();
        intent.setClass(context, FragmentFactory.class);
        intent.putExtra("key",key);
        context.startActivity(intent);
    }

    @Override
    public int layoutID() {
        return R.layout.activity_fragment_factory;
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        mBundle = intent.getExtras();
        if (intent != null){
            int fragmentId = intent.getIntExtra("key",-1);
            BaseFragment fragment = null;
            switch (fragmentId){

            }
            if (fragment != null)
                showFragment(fragment,R.id.fragments,false);
        }
    }

    private void showFragment(BaseFragment fragment, int LayoutId, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out, R.anim.push_right_in, R.anim.push_right_out);
        boolean existing = false;
        List<Fragment> fragments = fragmentManager.getFragments();
        Log.e("showFragment",fragment.TAG);

        if (fragments != null) {
            for (Fragment FRAGMENT : fragments) {
                BaseFragment baseFragment = (BaseFragment) FRAGMENT;
                if (baseFragment != null) {
                    if (null != fragmentManager.findFragmentByTag(fragment.getTag())) {
                        existing = true;
                    }

                    if (baseFragment.isVisible()) {
                        fragmentTransaction.hide(baseFragment);
                        baseFragment.setMenuVisibility(false);
                        baseFragment.setUserVisibleHint(false);
                    }
                } else {

                }


            }
        }
        BaseFragment baseFragment = (BaseFragment) fragmentManager.findFragmentByTag(fragment.getTag());

        if (existing) {
            if (baseFragment != null) {
                if (!baseFragment.isAdded()) {
                    fragmentTransaction.add(LayoutId, baseFragment, baseFragment.getTag());

                } else {
                    fragmentTransaction.show(baseFragment);
                }
            }

        } else {
            if (!fragment.isAdded()) {
                fragmentTransaction.add(LayoutId, fragment, fragment.getTag());


            } else {
                fragmentTransaction.show(fragment);
            }
        }

        fragment.setUserVisibleHint(true);
        fragment.setMenuVisibility(true);

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        fragmentTransaction.commitAllowingStateLoss();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBundle != null) {
            mBundle.clear();
        }

    }

}
