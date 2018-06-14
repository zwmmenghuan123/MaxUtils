package com.example.weimingzeng.maxutils.netUtils.api;

import com.example.weimingzeng.maxutils.netUtils.SzBaseResponse;
import com.example.weimingzeng.maxutils.netUtils.test.BookBean;
import com.example.weimingzeng.maxutils.netUtils.test.Top250Bean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Date: 2018/6/4
 * author:Weiming Max Zeng
 */
public interface BaseApi {

    public static final String Base_URL = "";

    //http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
    @GET
    Observable<String> getOriJsonData(@Url String url);

    @GET("v2/movie/top250")
    Observable<Top250Bean> getTop250(@Query("count") int count);

    @GET("v2/book/1220562")
    Observable<String> getBookString();

    @GET("v2/book/{bookId}")
    Observable<BookBean> getBook(@Path("bookId") String bookId);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

    @GET
    <k> Observable<SzBaseResponse<List<k>>> getData(@Url String url, Class<k> clazz);

    @GET
    <k> Observable<SzBaseResponse<List<k>>> getData(@Url String url, @QueryMap Map<String, String> maps, Class<k> clazz);

    @GET
    <k> Observable<SzBaseResponse<k>> executeGet(@Url String url, Class<k> clazz);

    @GET
    <k> Observable<SzBaseResponse<k>> executeGet(@Url String url, @QueryMap Map<String, String> maps, Class<k> clazz);

    @POST
    <k> Observable<SzBaseResponse<k>> post(@Url String url, @QueryMap Map<String, String> maps, Class<k> clazz);

    @POST
    Observable<ResponseBody> postJson(@Url String url, @Body RequestBody jsonStr);

    @Multipart
    @POST
    Observable<ResponseBody> upLoadFile(@Url String url, @Part("image\"; filename=\"image.jpg") RequestBody requestBody);

    @POST("{url}")
    Call<ResponseBody> uploadFiles(@Path("url") String url,
                                   @Part("filename") String description,
                                   @PartMap() Map<String, RequestBody> maps);
}
