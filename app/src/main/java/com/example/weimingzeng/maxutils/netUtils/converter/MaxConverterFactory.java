package com.example.weimingzeng.maxutils.netUtils.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


/**
 * Max的转换器
 * Created by weiming.zeng on 2017/12/22.
 */

public class MaxConverterFactory extends Converter.Factory {

    private final Gson gson;

    public static MaxConverterFactory create() {
        return create(new Gson());
    }

    public static MaxConverterFactory create(Gson gson) {
        return new MaxConverterFactory(gson);
    }

    private MaxConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new MaxResponseBodyConverter<>(gson, adapter, type, retrofit.baseUrl().url().toString());
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new MaxRequestBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return super.stringConverter(type, annotations, retrofit);
    }
}
