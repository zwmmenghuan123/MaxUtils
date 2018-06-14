package com.example.weimingzeng.maxutils.netUtils.interceptor;

import android.util.Log;

import com.example.weimingzeng.maxutils.netUtils.utils.SpfUtils;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Allen on 2017/5/11.
 * <p>
 *
 * @author Allen
 *         请求头里边添加cookie
 */

public class AddCookiesInterceptor implements Interceptor {
    String COOKIE = "cookie";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        //cookie放在sp中
        HashSet<String> preferences = (HashSet<String>) SpfUtils.get(COOKIE, new HashSet<String>());
        if (preferences != null) {
            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
                // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
                Log.v("RxHttpUtils", "Adding Header Cookie--->: " + cookie);
            }
        }
        return chain.proceed(builder.build());
    }

}
