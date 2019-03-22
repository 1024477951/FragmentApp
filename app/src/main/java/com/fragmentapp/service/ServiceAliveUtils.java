package com.fragmentapp.service;

import android.app.ActivityManager;
import android.content.Context;

import com.fragmentapp.App;

/**
 * Created by liuzhen on 2019/2/26.
 */

public class ServiceAliveUtils {

    public static boolean isServiceAlice() {
        boolean isServiceRunning = false;
        ActivityManager manager =
                (ActivityManager) App.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) {
            return true;
        }
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.fragmentapp.App.service.WatchOneService".equals(service.service.getClassName()) || "com.fragmentapp.App.service.WatchTwoService".equals(service.service.getClassName())) {
                isServiceRunning = true;
            }
        }
        return isServiceRunning;
    }

    public static boolean isServiceAlice(String serviceName) {
        boolean isServiceRunning = false;
        ActivityManager manager =
                (ActivityManager) App.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) {
            return true;
        }
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(service.service.getClassName())) {
                isServiceRunning = true;
            }
        }
        return isServiceRunning;
    }

}
