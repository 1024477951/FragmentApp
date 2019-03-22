package com.fragmentapp.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.fragmentapp.R;
import com.fragmentapp.aldl.WatchServiceConnection;
import com.orhanobut.logger.Logger;

/**
 * Created by liuzhen on 2019/2/26.
 */

public class WatchTwoService extends Service{

    private final static String TAG = "WatchOneService";
//    private NotificationManager notificationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new WatchServiceConnection.Stub() { };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.e(TAG, TAG+" onStartCommand");
//        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        //创建NotificationChannel
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            NotificationChannel channel = new NotificationChannel(TAG, TAG, NotificationManager.IMPORTANCE_HIGH);
//            notificationManager.createNotificationChannel(channel);
//        }
        startForeground(1, new Notification());
        // 绑定建立链接
        bindService(new Intent(this, WatchOneService.class), mServiceConnection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Logger.e(TAG, "Service:建立链接");
//            boolean isServiceRunning = ServiceAliveUtils.isServiceAlice();
//            if (!isServiceRunning) {
//                Intent i = new Intent(WatchTwoService.this,WatchOneService.class);
//                startService(i);
//            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            // 断开链接
            startService(new Intent(WatchTwoService.this,WatchOneService.class));
            // 重新绑定
            bindService(new Intent(WatchTwoService.this,WatchOneService.class), mServiceConnection, Context.BIND_IMPORTANT);
        }
    };

}
