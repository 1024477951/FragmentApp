package com.fragmentapp.home;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.fragmentapp.R;
import com.fragmentapp.base.BaseActivity;
import com.fragmentapp.helper.FragmentHelper;
import com.fragmentapp.home.adapter.MainAdapter;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private MainAdapter adapter;
    private SparseArray<CheckBox> menus = new SparseArray<>();

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    @Override
    public int layoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        adapter = new MainAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(FragmentHelper.getCount());
        viewPager.addOnPageChangeListener(this);

        addTab(findViewById(R.id.home),0);
        addTab(findViewById(R.id.contact),1);
        addTab(findViewById(R.id.community),2);
        addTab(findViewById(R.id.me),3);
        select(0);
    }

    public void click(View view){

        int position = 0;
        switch (view.getId()){
            case R.id.home:
                position = 0;
                break;
            case R.id.contact:
                position = 1;
                break;
            case R.id.community:
                position = 2;
                break;
            case R.id.me:
                position = 3;
                break;
        }
        select(position);
    }
    /**添加首页按钮缓存*/
    private void addTab(View view,int position){
        ViewGroup group = cover(view);
        CheckBox checkBox = cover(group.getChildAt(0));
        checkBox.setChecked(false);
        menus.put(position,checkBox);
    }
    /**选择菜单*/
    private void select(int position){
        if (menus.get(position).isChecked())
            return;//防止执行多次
//        Log.e("tag",position+"");
        for (int i = 0;i < menus.size();i++) {
            CheckBox box = menus.get(i);
            if (box == null)
                continue;
            if (i == position) {
                box.setChecked(true);
            }else
                box.setChecked(false);
        }
        viewPager.setCurrentItem(position, false);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        select(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
