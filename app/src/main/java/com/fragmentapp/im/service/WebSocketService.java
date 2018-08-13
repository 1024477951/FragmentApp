package com.fragmentapp.im.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.fragmentapp.http.RetrofitHelper;
import com.orhanobut.logger.Logger;

import java.net.URISyntaxException;
import java.util.Arrays;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;

/**
 * Created by liuzhen on 2018/8/13.
 */

public class WebSocketService extends Service {

    private Socket mSocket;
    private final String TAG = this.getClass().getSimpleName();

    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用 onStartCommand() 或 onBind() 之前）。
     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
     */
    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    /**
     * 每次通过startService()方法启动Service时都会被回调。
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.e(TAG,"创建WebSocket:");
        initWebSocket();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();

        /*可以指定关闭单个Emitter.Listener，也可以一次性全部关闭*/
//        mSocket.off(Socket.EVENT_CONNECT, onConnect);
//        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
//        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
//        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.off();
        Logger.e(TAG,"WebSocket销毁了:");
    }

    private void initWebSocket() {
        if (mSocket == null) {
            try {
                IO.Options opts = new IO.Options();
                opts.forceNew = true;
                opts.transports = new String[]{WebSocket.NAME};//polling/websocket
                opts.query = "uid=" + 1801 + "&auth=androidA0:32:99:4E:66:77";
                opts.reconnection = true;// 是否自动重连
                opts.reconnectionAttempts = 3;// 重连尝试次数
                opts.reconnectionDelay = 1000;// 重连间隔
                opts.timeout = 20000;// 连接超时时间 ms
                mSocket = IO.socket(RetrofitHelper.BASE_URL_USER,opts);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            mSocket.on(Socket.EVENT_CONNECT, onConnect);
            mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
            mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
            mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
            mSocket.on("message", new Emitter.Listener() { //监听回调函数
                @Override
                public void call(Object... args) {

                }
            });

            mSocket.connect();
        }
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Logger.e(TAG," WebSocket连接:" + Arrays.toString(args));
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Logger.e(TAG," WebSocket断开:" + Arrays.toString(args));
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            Logger.e(TAG,"WebSocket连接异常:" + Arrays.toString(args));
            /*如果想长连接，则不用关闭service*/
//            stopSelf();
        }
    };
}