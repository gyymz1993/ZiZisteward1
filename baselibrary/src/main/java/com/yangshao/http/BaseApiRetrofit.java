package com.yangshao.http;


import android.annotation.SuppressLint;

import com.yangshao.base.BaseApplication;
import com.yangshao.utils.L_;
import com.yangshao.utils.NetUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @创建者 CSDN_LQR
 * @描述 配置Retrofit（配置网络缓存cache、配置持久cookie免登录）
 */

public class BaseApiRetrofit {

    /*请求路径打印*/
    private static OkHttpClient getOkHttpClient() {
        File httpCacheDir = new File(BaseApplication.getApplication().getCacheDir(), "response");
        int cacheSize = 10 * 1024 * 1024;// 10 MiB
        Cache cache = new Cache(httpCacheDir, cacheSize);
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
                .Builder();
        httpClientBuilder.addInterceptor(new LoggingInterceptor());
        httpClientBuilder.addInterceptor(new CacheContrl());
        httpClientBuilder.cache(cache);
        return httpClientBuilder.build();
    }


    private static class CacheContrl implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //通过 CacheControl 控制缓存数据
            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);//这个是控制缓存的最大生命时间
            cacheBuilder.maxStale(365, TimeUnit.DAYS);//这个是控制缓存的过时时间
            CacheControl cacheControl = cacheBuilder.build();
            //设置拦截器
            Request request = chain.request();
            Request.Builder builder = request.newBuilder();
            //header配置
            //builder.addHeader("Content-Type", "application/json");
            if (!NetUtils.isConnected()) {
                builder.cacheControl(cacheControl);
            }
            Response originalResponse = chain.proceed(request);
            if (NetUtils.isConnected()) {
                int maxAge = 0;//read from cache
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28;//tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .removeHeader("Prama")
                        .header("Cache-Control", "poublic, only-if-cached, max-stale=" + maxStale)
                        .build();
            }

        }
    }


    private static class LoggingInterceptor implements Interceptor {
        @SuppressLint("DefaultLocale")
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Request request = chain.request();
            long t1 = System.nanoTime();//请求发起的时间
            L_.e(String.format("发送请求 %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();//收到响应的时间
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            L_.e(String.format("接收响应: [%s] %n返回json:【%s】 %.1fms%n%s",
                    response.request().url(),
                    responseBody.string(),
                    (t2 - t1) / 1e6d,
                    response.headers()));
            return response;
        }

    }


}
