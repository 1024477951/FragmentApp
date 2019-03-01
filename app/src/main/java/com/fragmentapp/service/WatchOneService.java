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
 * client在请求和server通信时，首先会去查serviceManager中是否有这个service，有的话service通过bind-driver提供一份对应service的代理，
 * client不会直接操作server的方法，而是通过操作server对应的代理对象，代理对象有和server一样的方法。
 * client向server发送数据时打包成parcel包，交给bind-driver。每个server都由serviceManage管理，注册在serviceMananger中，serviceManager会不断查询bind-driver中是否有数据发给指定的service
 */

public class WatchOneService extends Service{

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
        bindService(new Intent(this, WatchTwoService.class), mServiceConnection, Context.BIND_IMPORTANT);
        return START_STICKY;//在Service被关闭后,重新开启Service
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Logger.e(TAG, "Service:建立链接");
//            boolean isServiceRunning = ServiceAliveUtils.isServiceAlice();
//            if (!isServiceRunning) {
//                Intent i = new Intent(WatchOneService.this, WatchTwoService.class);
//                startService(i);
//            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            // 断开链接
            startService(new Intent(WatchOneService.this, WatchTwoService.class));
            // 重新绑定
            bindService(new Intent(WatchOneService.this, WatchTwoService.class), mServiceConnection, Context.BIND_IMPORTANT);
        }
    };

}
