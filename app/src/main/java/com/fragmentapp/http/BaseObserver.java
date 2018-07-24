package com.fragmentapp.http;

import android.os.Looper;

import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by liuzhen on 2017/11/3.
 */

public abstract class BaseObserver<T extends BaseResponses> implements Observer<T> {
    String TAG = getClass().getSimpleName();

    @Override
    public void onSubscribe(Disposable d) {
        Logger.e(TAG, "onSubscribe");
    }

    @Override
    public void onNext(T t) {
        Logger.e(TAG, "onNext"+t);
        if (t.getStatus() == 200) {
            onNextResponse(t);
        } else {
            Logger.e(TAG, "ErrorStatus:" + t.getStatus() + "ErrorInfo" + t.getInfo());
            onErrorResponse(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        Logger.e(TAG, "onError" + e.toString());
        onNetWorkError("onError 网络超时，请重新尝试--"+e.getMessage());
        if (Looper.myLooper() == null) {
            Looper.prepare();
            Looper.loop();
        }

    }

    @Override
    public void onComplete() {
        Logger.e(TAG, "onComplete");
    }

    /**返回成功*/
    public abstract void onNextResponse(T t);
    /**接口失败信息*/
    public abstract void onErrorResponse(T t);
    /**网络错误*/
    public abstract void onNetWorkError(String val);
}

