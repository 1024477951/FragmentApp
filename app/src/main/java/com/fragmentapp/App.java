package com.fragmentapp;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.telephony.TelephonyManager;

import com.bumptech.glide.Glide;
import com.fragmentapp.helper.AppManager;
import com.fragmentapp.helper.MacUtils;
import com.fragmentapp.helper.SharedPreferencesUtils;
import com.fragmentapp.im.service.WebSocketService;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;

import java.util.UUID;

/**
 * Created by liuzhen on 2017/11/8.
 */

public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //第一：默认初始化
//        Bmob.initialize(this, "7a0cb418fc0379a4b199c6b1a51e5ca9");
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);


        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("tag-")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }

    }

    public static App getInstance(){
        return instance;
    }

    @SuppressLint("MissingPermission")
    public String getDerviceID() {
        String DerviceID = "";
        // mac地址
        DerviceID = MacUtils.getMacAddr();
        if (DerviceID == null || "".equals(DerviceID)) {
            Logger.e("MacUtils.getMacAddr()=null");
            DerviceID = MacUtils.getMac();
        }
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        //IMEI（imei）
        if (DerviceID == null || "".equals(DerviceID)) {
            Logger.e("MacUtils.getMac()=null");
            try {
                DerviceID = tm.getDeviceId();
            } catch (Exception e) {
                Logger.e(e.toString());
            }

        }

        //序列号（sn）
        if (DerviceID == null || "".equals(DerviceID)) {
            Logger.e("IMEI（imei）=null");

            try {
                DerviceID = tm.getSimSerialNumber();
            } catch (Exception e) {
                Logger.e(e.toString());
            }
        }
        /**
         * 得到全局唯一UUID
         */
        if (DerviceID == null || "".equals(DerviceID)) {
            Logger.e("序列号（sn）=null");
            DerviceID = SharedPreferencesUtils.getParam("uuid", null);

            if (DerviceID == null || "".equals(DerviceID)) {
                DerviceID = UUID.randomUUID().toString();
                SharedPreferencesUtils.setParam("uuid", DerviceID);
            }
            Logger.e("uuid:" + DerviceID);
        }

        return DerviceID;
    }

    @Override
    public void onTrimMemory(int level) {
        Logger.d("end----onTrimMemory "+level + " activity num "+AppManager.getAppManager().getActivityCount());
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        if (level == TRIM_MEMORY_MODERATE){

        }
        if (level == TRIM_MEMORY_RUNNING_MODERATE){
            Intent intent = new Intent(instance, WebSocketService.class);
            instance.stopService(intent);
            AppManager.getAppManager().finishAllActivity();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Logger.e("end---- onTerminate activity num "+AppManager.getAppManager().getActivityCount());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Logger.d("end----onLowMemory activity num "+AppManager.getAppManager().getActivityCount());
    }

}
