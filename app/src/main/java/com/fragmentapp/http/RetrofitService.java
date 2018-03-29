package com.fragmentapp.http;

import com.fragmentapp.home.bean.ArticleDataBean;
import com.fragmentapp.login.bean.LoginDataBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by liuzhen on 2017/11/3.
 */

public interface RetrofitService {

    @FormUrlEncoded
    @POST("/Login")
    Observable<BaseResponses<LoginDataBean>> login(@FieldMap Map<String, String> map);

    //获取文章列表信息
    @POST("/Article/getArticleList")
    Observable<BaseResponses<ArticleDataBean>> getArticleList(@Header("HC-ACCESS-TOKEN") String header, @QueryMap Map<String, Integer> map);

    //添加Headers：使用同样的键url_name；使用不同值user或pay，对应账户服地址和支付服地址。
//    @Headers({"url_name:pay"})
//    @POST("pay/getorder")
//    Observable<BaseResponses> getOrder(@QueryMap Map<String, String> map);

}
