package com.example.weimingzeng.maxutils.netUtils.converter;

import com.example.weimingzeng.maxutils.netUtils.netException.ApiException;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by weiming.zeng on 2017/12/22.
 */

public abstract class BaseResponseBodyConverter <T> implements Converter<ResponseBody, T> {

    protected Gson gson;
    private final TypeAdapter<T> adapter;
    private static final Charset UTF_8 = Charset.forName("UTF-8");


    BaseResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String body = value.string();
        T result = null;
        try {
            result = parseJson(body);
        } catch (Exception e) {
            e.printStackTrace();
            throw (ApiException)e;
        }
        if (result != null) {
            return result;
        }
        MediaType contentType = value.contentType();
        Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
        Reader reader = new InputStreamReader(new ByteArrayInputStream(body.getBytes()), charset);
        JsonReader jsonReader = gson.newJsonReader(reader);
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }


    public abstract T parseJson(String json) throws Exception;

    public void pareseError(Exception e) {

    }
}
