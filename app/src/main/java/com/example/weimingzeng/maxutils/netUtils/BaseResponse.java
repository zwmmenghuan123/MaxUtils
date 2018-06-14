package com.example.weimingzeng.maxutils.netUtils;

import java.io.Serializable;

/**
 * Created by weiming.zeng on 2017/12/20.
 */

public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1183820018943887365L;

    private String error = "0";

    private String reason;

    private String message;

    private int token_status;

    //远程操控路由器时，路由器响应并透传的消息
    private String data;

    private String httpCode = "200";

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getToken_status() {
        return token_status;
    }

    public void setToken_status(int token_status) {
        this.token_status = token_status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(String httpCode) {
        this.httpCode = httpCode;
    }
}
