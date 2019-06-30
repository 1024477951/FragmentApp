package com.fragmentapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
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
import com.fragmentapp.helper.HtmlUtils;
import com.fragmentapp.home.adapter.MainAdapter;
import com.fragmentapp.home.fragment.HomeFragment;
import com.fragmentapp.im.service.WebSocketService;
import com.fragmentapp.service.WatchJobService;
import com.fragmentapp.service.WatchOneService;
import com.fragmentapp.service.WatchTwoService;
import com.orhanobut.logger.Logger;

import java.util.Calendar;

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

    private PowerManager.WakeLock mWakeLock;
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
//        } else {
//            LoginActivity.start(context);
//            finish();
//        }
        boolean isGuide = GuideUtil.getInstance().isGuide(GuideUtil.getInstance().main);
        if (isGuide) {
            showGuideView(dynamic);
        }
//        startAllServices();
        getLock(this);
//        enterSetting();
        String test = "肯讷河就下你说呢\n&nbsp;\n华东交大煎<p>111</p>鸡蛋饼\n&nbsp;\n喜欢喜<style width='66'></style>欢夫好河山\\n雪花秀你先弄你的\\n&nbsp;\\n&nbsp;";
//        String test = "山\\n雪花秀你先弄你的\\n&nbsp;\\n&nbsp;";

        String html = HtmlUtils.delHTMLTag(test);
        Log.e("=====> ",test);
        Log.e("=====> ",html);
    }

    /**
     * 开启所有Service
     */
    private void startAllServices(){
        Intent intent = new Intent(this, WebSocketService.class);
        startService(intent);
        startService(new Intent(this, WatchOneService.class));
        startService(new Intent(this, WatchTwoService.class));
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP) {
            //版本必须大于5.0
            startService(new Intent(this, WatchJobService.class));
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
                if ((now - firstPressTime) > 500) {
                    firstPressTime = now;
                } else {
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
        releaseLock();
    }

    /**
     * 进入白名单列表上一层级
     **/
    private void enterSetting() {
        try {
            Intent intent = new Intent();
            intent.setAction("com.android.settings.action.SETTINGS");
            intent.addCategory("com.android.settings.category");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.android.settings");
            intent.setClassName("com.android.settings", "com.android.settings.Settings$PowerUsageSummaryActivity");
            startActivity(intent);
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
    }

    /**
     * 同步方法   得到休眠锁
     * 保证息屏后不被释放资源杀死
     */
    synchronized private void getLock(Context context) {
        if (mWakeLock == null) {
            PowerManager mgr = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WatchOneService.class.getName());
            mWakeLock.setReferenceCounted(true);
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis((System.currentTimeMillis()));
            int hour = c.get(Calendar.HOUR_OF_DAY);
            if (hour >= 23 || hour <= 6) {
                mWakeLock.acquire(5000);
            } else {
                mWakeLock.acquire(300000);
            }
        }
        Logger.v(TAG, "get lock");
    }

    synchronized private void releaseLock() {
        if (mWakeLock != null) {
            if (mWakeLock.isHeld()) {
                mWakeLock.release();
                Logger.v(TAG, "release lock");
            }
            mWakeLock = null;
        }
    }

}
