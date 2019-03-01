package com.fragmentapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.orhanobut.logger.Logger;

/**
 * Created by liuzhen on 2019/2/28.
 */

public class WatchServiceReceiver extends BroadcastReceiver{

    private final static String TAG = "WatchServiceReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.e(TAG, TAG+" onReceive");
        Intent mIntent = new Intent(context,WatchOneService.class);
        context.startService(mIntent);
    }
}
