package com.example.weimingzeng.maxutils.netUtils;


import com.example.weimingzeng.maxutils.netUtils.converter.MaxConverterFactory;
import com.example.weimingzeng.maxutils.netUtils.interceptor.AddCookiesInterceptor;
import com.example.weimingzeng.maxutils.netUtils.interceptor.CacheInterceptor;
import com.example.weimingzeng.maxutils.netUtils.interceptor.HeaderInterceptor;
import com.example.weimingzeng.maxutils.netUtils.interceptor.ReceivedCookiesInterceptor;
import com.example.weimingzeng.maxutils.netUtils.interceptor.TokenInterceptor;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 *
 * Created by weiming.zeng on 2017/12/19.
 */

public class RetrofitHelper {
    private static final int TIMEOUT_READ = 20;
    private static final int TIMEOUT_CONNECTION = 10;

    //缓存配置
    private static String cachePath = "Environment.getExternalStorageDirectory().getPath() + \"/rxHttpCacheData\"";//默认路径，可修改
    private static long cacheMaxSize = 1024 * 1024 * 100;

    private static final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(interceptor)    //打印日志
//            .addInterceptor(new HeaderInterceptor(new HashMap<String, Object>()))//Map里配置请求头
//            .addInterceptor(new CacheInterceptor())
//            .addInterceptor(new TokenInterceptor())
            //添加cookie拦截器
//            .addInterceptor(new AddCookiesInterceptor())
//            .addInterceptor(new ReceivedCookiesInterceptor())
            .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true) //失败重连
            .cache(new Cache(new File(cachePath), cacheMaxSize))
            //信任所有证书,不安全有风险
//            .sslSocketFactory()
            .build();

    public static <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MaxConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

    public static <T> T createApi(Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/")
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())//将responseBody的json资源转换成api里的对象
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

//    private X509TrustManager getX509TrustManager() {
//        X509TrustManager manager;
//    }
}
