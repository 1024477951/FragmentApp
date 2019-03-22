package com.fragmentapp;

import android.content.Context;
import android.content.Intent;
import androidx.viewpager.widget.ViewPager;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.HighlightOptions;
import com.app.hubert.guide.model.RelativeGuide;
import com.fragmentapp.base.BaseActivity;
import com.fragmentapp.helper.EmptyLayout;
import com.fragmentapp.helper.GuideUtil;
import com.fragmentapp.home.adapter.MainAdapter;
import com.fragmentapp.home.fragment.HomeFragment;
import com.fragmentapp.im.service.WebSocketService;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.home)
    View home;
    @BindView(R.id.contact)
    View contact;
    @BindView(R.id.dynamic)
    View dynamic;
    @BindView(R.id.me)
    View me;

    private long firstPressTime = 0;

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
//        String token = SharedPreferencesUtils.getParam("token");
//        if (token != null) {
            adapter = new MainAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(TabHelper.getCount());
            viewPager.addOnPageChangeListener(this);

            addTab(home, 0);
            addTab(contact, 1);
            addTab(dynamic, 2);
            addTab(me, 3);
            select(0);

            emptyLayout.setCallBack(new EmptyLayout.CallBack() {
                @Override
                public void click() {
                    init();
                }
            });

            Intent intent = new Intent(this, WebSocketService.class);
            startService(intent);
//        } else {
//            LoginActivity.start(context);
//            finish();
//        }
        boolean isGuide = GuideUtil.getInstance().isGuide(GuideUtil.getInstance().main);
        if (isGuide) {
            showGuideView(dynamic);
        }
    }

    public void showGuideView(View view) {
        HighlightOptions options = new HighlightOptions.Builder()
                .setRelativeGuide(new RelativeGuide(R.layout.layout_alert, Gravity.TOP, 0) {
                    @Override
                    protected void onLayoutInflated(View view) {

                    }
                })
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        LoginActivity.start(context);
                    }
                })
                .build();
        GuidePage page = GuidePage.newInstance()
                .addHighLightWithOptions(view, options);
        NewbieGuide.with(this)
                .setLabel("relative")
                .alwaysShow(true)//总是显示，调试时可以打开
                .addGuidePage(page)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void click(View view) {

        int position = -1;
        switch (view.getId()) {
            case R.id.home:
                position = 0;
                long now = System.currentTimeMillis();
                if ((now - firstPressTime) > 500){
                    firstPressTime = now;
                }else{
                    HomeFragment fragment = cover(adapter.getItem(position));
                    fragment.click();
                }
                break;
            case R.id.contact:
                position = 1;
                break;
            case R.id.dynamic:
                position = 2;
                break;
            case R.id.me:
                position = 3;
                break;
        }
        if (position >= 0) select(position);
    }

    /**
     * 添加首页按钮缓存
     */
    private void addTab(View view, int position) {
        CheckBox checkBox = view.findViewWithTag("check");
        checkBox.setChecked(false);
        menus.put(position, checkBox);
    }

    /**
     * 选择菜单
     */
    private void select(int position) {
        if (menus.get(position).isChecked())
            return;//防止执行多次
//        Log.e("tag",position+"");
        for (int i = 0; i < menus.size(); i++) {
            CheckBox box = menus.get(i);
            if (box == null)
                continue;
            if (i == position) {
                box.setChecked(true);
            } else
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        menus.clear();
        menus = null;
    }
}
