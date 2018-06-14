package com.example.weimingzeng.maxutils.netUtils.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 *
 * Created by weiming.zeng on 2017/12/22.
 */

public class MaxRequestBodyConverter<T> extends BaseRequestBodyConverter<T> {
    MaxRequestBodyConverter(Gson gson, TypeAdapter adapter) {
        super(gson, adapter);
    }
}
