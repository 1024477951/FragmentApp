package com.fragmentapp.http;

import android.util.Log;

import com.fragmentapp.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liuzhen on 2017/11/3.
 */

public class RetrofitHelper {

    private static final String BASE_URL_USER = "http://testapi.hanmaker.com";
//    private static final String BASE_URL_USER = "https://api.hanmaker.com";
    private static final String BASE_URL_PAY = "https://www.222.com/";

    private static final long TIME_OUT = 5000;

    private RetrofitHelper(){}

    private static class SingleHolder {
        private static final RetrofitHelper INSTANCE = new RetrofitHelper();
    }

    public static final RetrofitHelper getInstance() {
        return SingleHolder.INSTANCE;
    }

    private static final long DEFAULT_TIMEOUT = 15L;

    final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()// 调用serializeNulls方法，改变gson对象的默认行为，null值将被输出
            .create();

    //addInterceptor:设置应用拦截器，可用于设置公共参数，头信息，日志拦截等
    //addNetworkInterceptor:网络拦截器，可以用于重试或重写
    //setLevel NONE(不记录) BASIC(请求/响应行)  HEADER(请求/响应行 + 头)  BODY(请求/响应行 + 头 + 体)
    //cookieJar:保持在同一个会话里面
    //TimeUnit.SECONDS秒做单位
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .retryOnConnectionFailure(true)//错误重联
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    //获取request
                    Request request = chain.request();
                    if (BuildConfig.DEBUG) {
                        Logger.d("Interceptor",chain);
                    }
                    //获取request的创建者builder
                    Request.Builder builder = request.newBuilder();
                    //从request中获取headers，通过给定的键url_name
                    List<String> headerValues = request.headers("url_name");
                    if (headerValues != null && headerValues.size() > 0) {
                        //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
                        builder.removeHeader("url_name");

                        //匹配获得新的BaseUrl
                        String headerValue = headerValues.get(0);
                        HttpUrl newBaseUrl = null;
                        if ("user".equals(headerValue)) {
                            newBaseUrl = HttpUrl.parse(BASE_URL_USER);
                        } else if ("pay".equals(headerValue)) {
                            newBaseUrl = HttpUrl.parse(BASE_URL_PAY);
                        } else{
                            newBaseUrl = HttpUrl.parse(BASE_URL_USER);
                        }

                        //从request中获取原有的HttpUrl实例oldHttpUrl
                        HttpUrl oldHttpUrl = request.url();
                        //重建新的HttpUrl，修改需要修改的url部分
                        HttpUrl newFullUrl = oldHttpUrl
                                .newBuilder()
                                .scheme(newBaseUrl.scheme())//设置网络协议
                                .host(newBaseUrl.host())//更换主机名
                                .port(newBaseUrl.port())//更换端口
                                .build();

                        //重建这个request，通过builder.url(newFullUrl).build()；
                        //然后返回一个response至此结束修改
                        return chain.proceed(builder.url(newFullUrl).build());
                    } else {
                        return chain.proceed(request);
                    }
                }
            })
            .build();

    private static Retrofit retrofit = null;
    private static <T> T createApi(Class<T> clazz) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_USER)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(clazz);
    }
    private RetrofitService service = null;
    public RetrofitService getService(){
        if (service == null)
            service = createApi(RetrofitService.class);
        return service;
    }

}
