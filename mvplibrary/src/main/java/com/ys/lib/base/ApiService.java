package com.ys.lib.base;

import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;
/**
 * @author: gyymz1993
 * 创建时间：2017/5/3 22:46
 * @version
 *
 **/
public interface ApiService {

        /*需要加密目前这种方法可行*/

        @GET
        Observable<String> getData(@Url String url);

        @POST
        Observable<String> postData(
                @Url String url
        );


        @GET("{url}")
        Observable<String> loadData(@Path("url") String url,
                                    @PartMap Map<String, String> map);


        // TODO: 2017/5/8  待测试
        //post表单传递，map，就是我们一般用到的
        @POST("url")
        @FormUrlEncoded
        Call<ResponseBody> doLogin(@FieldMap Map<String,String> params);


        // TODO: 2017/5/8  待测试
        @Multipart
        @POST( "{url}")
        Observable<String>  uploadFile(
                @Path("url") String url,
                @PartMap Map<String, RequestBody> map
        );

        /**多文件多参数上传**/
        @Multipart
        @POST("uploadSight")
        Observable<String> uploadFiles(@Part List<MultipartBody.Part> partList);


        //// TODO: 2017/5/8  待测试 
        /**
         * 单文件上传  ParamKey.  自定义 参数  TOKEN
         **/
        @Multipart
        @POST("")
        Observable<String> uploadFile(@Part MultipartBody.Part part, @Part("TOKEN") RequestBody token);


        //图片上传
        @Multipart
        @POST("uploadSight")
        Observable<String> updateImage(@PartMap Map<String,RequestBody> params);


        //可以传输所有实体类转JSon
        @POST("user/login")
        Observable<String> requsetJson(@Body RequestBody body);


}
