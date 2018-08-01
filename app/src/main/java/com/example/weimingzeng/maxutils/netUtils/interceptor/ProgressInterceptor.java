package com.example.weimingzeng.maxutils.netUtils.interceptor;

import com.example.weimingzeng.maxutils.netUtils.ProgressListener;
import com.example.weimingzeng.maxutils.netUtils.ProgressResponseBody;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Date: 2018/6/13
 * author:Weiming Max Zeng
 */
public class ProgressInterceptor implements Interceptor {

    private ProgressListener downloadListener;

    public ProgressInterceptor(ProgressListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        return response.newBuilder().body(new ProgressResponseBody(response.body(), downloadListener)).build();
    }
}
