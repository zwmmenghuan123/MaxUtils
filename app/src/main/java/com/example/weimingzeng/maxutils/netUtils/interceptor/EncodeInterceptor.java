package com.example.weimingzeng.maxutils.netUtils.interceptor;

import com.example.weimingzeng.maxutils.netUtils.utils.CommonUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Date: 2018/6/28
 * author:Weiming Max Zeng
 */
public class EncodeInterceptor implements Interceptor {
    private String newHost = "127.0.0.1";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url();

        //http://127.0.0.1/test/upload/img?userName=xiaoming&userPassword=12345
        String scheme = url.scheme();//  http https
        String host = url.host();//   127.0.0.1
        String path = url.encodedPath();//  /test/upload/img
        String query = url.encodedQuery();//  userName=xiaoming&userPassword=12345

        StringBuffer sb = new StringBuffer();
        sb.append(scheme).append(newHost).append(path).append("?");
        //获取查询参数
        Set<String> queryList = url.queryParameterNames();
        Iterator<String> iterator = queryList.iterator();

        for (int i = 0; i < queryList.size(); i++) {

            String queryName = iterator.next();
            sb.append(queryName).append("=");
            //获取参数value进行加密
            String queryKey = url.queryParameter(queryName);
            //对query的key进行加密
            sb.append(CommonUtils.MD5(queryKey));
            if (iterator.hasNext()) {
                sb.append("&");
            }
        }
        //对请求体进行加密
        String bodyString = requestBodyToString(request.body());
        String encodeBody = CommonUtils.MD5(bodyString);
        RequestBody newBody = RequestBody.create(MediaType.parse("application/json"), encodeBody);
        String newUrl = sb.toString();
        Request.Builder builder = request.newBuilder()
                .post(newBody)
                .url(newUrl);

        return chain.proceed(builder.build());
    }

    public static String requestBodyToString(RequestBody requestBody) throws IOException {
        if (requestBody == null) {
            return null;
        }
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        return buffer.readUtf8();
    }

}

