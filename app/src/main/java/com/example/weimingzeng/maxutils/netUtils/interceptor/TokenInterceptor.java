package com.example.weimingzeng.maxutils.netUtils.interceptor;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 处理异常问题拦截器
 * Created by weiming.zeng on 2017/12/26.
 */

public class TokenInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final int SZ_SUCCESS_CODE = 200;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response originalResponse = chain.proceed(request);
        ResponseBody responseBody = originalResponse.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // request the entire body.
        Buffer buffer = source.buffer();
        String bodyStr = buffer.clone().readString(Charset.forName("UTF-8"));   //克隆数据，因为response.body().string()只能调用一次
        JSONObject obj = JSON.parseObject(bodyStr);
        String url = originalResponse.request().url().toString();
        //处理斐讯云响应
//        if (url.startsWith(UrlConfig.CloudAccountUrl.URL_HOST)) {
//            handleFxResponse(bodyStr, originalResponse);
//        }

        //处理深圳后台响应
//        if (url.startsWith(UrlConfig.SzUrl.URL_HOST)) {
//            handleSzResponse(bodyStr, originalResponse);
//        }
        return originalResponse;
    }

//    private void handleFxResponse(String bodyStr, Response response) {
//        if (!response.isSuccessful()) {
//            ErrorHandler.toUiError(Err2MsgUtils.CODE_UNKNOW_ERROR, null, response.request());
//            return;
//        }
//        FxResponse fxObj = null;
//        try {
//            fxObj = JSON.parseObject(bodyStr, FxResponse.class);
//        } catch (Exception e) {
//            LogUtils.debug(e);
//        }
//        if (fxObj == null) {
//            ErrorHandler.toUiError(Err2MsgUtils.CODE_PARSE_ERROR, null, response.request());
//            return;
//        }
//
//        String error = fxObj.getError();
//        int tokenStatus = fxObj.getToken_status();
//        String httpCode = fxObj.getHttpCode();
//        if (error.equals("0") && tokenStatus == 0 && httpCode.equals("200")) {
//            return;
//        } else if (error.equals("26") || error.equals("30") || tokenStatus > 0) {
//            ErrorHandler.handleTokenExpire(error, fxObj.getReason(), response);
////            toUiError(Err2MsgUtils.CODE_TOKEN_TIMEOUT, null, response.request());
//        } else {
//            ErrorHandler.toUiError(error, null, response.request());
//        }
//    }
//
//    public void handleSzResponse(String bodyStr, Response response) {
//        SzResponse szObj;
//        try {
//            szObj = JSON.parseObject(bodyStr, SzResponse.class);
//        } catch (Exception e) {
//            ErrorHandler.toUiError(Err2MsgUtils.CODE_PARSE_ERROR, null, response.request());
//            return;
//        }
//
//        int status = szObj.getStatus();
//        String message = szObj.getMessage();
//        String resultStr = szObj.getResult();
//
//        if (status == SZ_SUCCESS_CODE) {
//            return;
//        } else if (status >= 10003 && status <= 10005) {    //token过期
//            ErrorHandler.handleTokenExpire(status + "", message, response);
//        } else if (status == 10006) {  //被踢
//            Log.d("phresult", "parsesz result: " + resultStr);
//            LogoutReason reason = JSONObject.parseObject(resultStr, LogoutReason.class);
//            ErrorHandler.handleTokenExpire(status + "", reason == null ? "" : reason.getReason(), response);
//        } else {
//            ErrorHandler.toUiError(String.valueOf(status), "服务异常，请稍候再试(" + status + ")", response.request());
//        }
//    }
}
