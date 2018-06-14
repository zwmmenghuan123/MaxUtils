package com.example.weimingzeng.maxutils.netUtils.converter;

import com.alibaba.fastjson.JSON;
import com.example.weimingzeng.maxutils.netUtils.SzBaseResponse;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.lang.reflect.Type;

/**
 * Created by weiming.zeng on 2017/12/22.
 */

public class MaxResponseBodyConverter<T> extends BaseResponseBodyConverter<SzBaseResponse> {
    private static final String TAG = "MaxResponseBodyConverter";
    private static final int SZ_SUCCESS_CODE = 200;
    private String url;
    private Type type;

    MaxResponseBodyConverter(Gson gson, TypeAdapter<T> adapter, Type type, String url) {
        super(gson, (TypeAdapter<SzBaseResponse>) adapter);
        this.url = url;
        this.type = type;
    }

    @Override
    public SzBaseResponse parseJson(String json) {
        T t;
        //处理斐讯云响应
//        if (url.startsWith(UrlConfig.CloudAccountUrl.URL_HOST)) {
        try {
            Class clazz = ((Class) type);
            t = (T) JSON.parseObject(json, clazz);
        } catch (Exception e) {
            pareseError(e);
            return null;
        }
        SzBaseResponse result = new SzBaseResponse();
        result.setResult(t);
        return result;
//        }
//        if (url.startsWith(UrlConfig.SzUrl.URL_HOST)) { //处理深圳后台响应
//            SzBaseResponse response = gson.fromJson(json, SzBaseResponse.class);
//            if (response.getStatus() != SZ_SUCCESS_CODE) {
//                handleException(response);
//            }
//            return response;
//        }
    }


}
