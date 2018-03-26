package com.fragmentapp.def.home;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

    public static final int SEARCH = 0;

    public static void start(Context context, int key) {
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
        if (intent != null){
            int fragmentId = intent.getIntExtra("key",-1);
            BaseFragment fragment = null;
            switch (fragmentId){
                case SEARCH:
                    break;
            }
            if (fragment != null)
                showFragment(fragment,R.id.fragments,false);
        }
    }

    private void showFragment(BaseFragment fragment, int LayoutId, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out, R.anim.push_right_in, R.anim.push_right_out);

        List<Fragment> fragments = fragmentManager.getFragments();
        Log.e("tag",fragment.TAG);

        if (fragments != null) {
            for (Fragment FRAGMENT : fragments) {
                BaseFragment baseFragment = (BaseFragment) FRAGMENT;
                if (baseFragment != null) {
                    if (null != fragmentManager.findFragmentByTag(fragment.TAG)) {
                        if (baseFragment != null) {

                            if (!baseFragment.isAdded()) {
                                fragmentTransaction.add(LayoutId, baseFragment, baseFragment.TAG);
                            } else {
                                fragmentTransaction.show(baseFragment);
                            }
                        }
                    } else {
                        if (!fragment.isAdded()) {
                            fragmentTransaction.add(LayoutId, fragment, fragment.TAG);
                        } else {
                            fragmentTransaction.show(fragment);
                        }
                    }
                } else {
                    Log.e("fragmentManager", "null");
                }

            }
        }

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        fragmentTransaction.commitAllowingStateLoss();

    }

}
