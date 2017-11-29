package com.fragmentapp.http;

import com.fragmentapp.login.bean.PersonBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by liuzhen on 2017/11/3.
 */

public interface RetrofitService {

    @POST("/Login")
    Observable<PersonBean> login(@QueryMap Map<String, String> map);

    //添加Headers：使用同样的键url_name；使用不同值user或pay，对应账户服地址和支付服地址。
//    @Headers({"url_name:pay"})
//    @POST("pay/getorder")
//    Observable<BaseResponses> getOrder(@QueryMap Map<String, String> map);

}
