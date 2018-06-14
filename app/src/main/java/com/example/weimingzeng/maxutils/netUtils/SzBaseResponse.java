package com.example.weimingzeng.maxutils.netUtils;

/**
 *
 * Created by weiming.zeng on 2017/12/21.
 */

public class SzBaseResponse<T> extends BaseSzResponse{
    private static final long serialVersionUID = -2686590170564090399L;

    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }


}
