package com.ys.lib.base;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.ls51.Convert;
import com.shove.security.Encrypt;
import com.yangshao.utils.L_;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class DecodeConverterFactory extends Converter.Factory {
    public static DecodeConverterFactory create() {
        return create(new Gson());
    }
    public static DecodeConverterFactory create(Gson gson) {
        return new DecodeConverterFactory(gson);
    }
    private final Gson gson;
    private DecodeConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        L_.e("响应requestBodyConverter");
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new JsonResponseBodyConverter<>(gson, adapter); //响应
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations,
                                                          Retrofit retrofit) {
        L_.e("请求requestBodyConverter");
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new JsonRequestBodyConverter<>(gson, adapter); //请求
    }

    /**
     * 自定义请求RequestBody
     */
    class JsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private final Gson gson;
        private final TypeAdapter<T> adapter;

        /**
         * 构造器
         */

        public JsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }


        @Override
        public RequestBody convert(T value) throws IOException {
            //加密
            L_.e("加密"+value.toString());

            // RequestBody.create(MEDIA_TYPE,  url);
            return null;
        }
    }


    /**
     * 自定义响应ResponseBody
     */
    public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final Gson mGson;//gson对象
        private final TypeAdapter<T> adapter;

        /**
         * 构造器
         */
        public JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.mGson = gson;
            this.adapter = adapter;
        }

        /**
         * 转换
         *
         * @param responseBody
         * @return
         * @throws IOException
         */
        @Override
        public T convert(ResponseBody responseBody) throws IOException {
            String response = responseBody.string();
            L_.e("解密"+response);
            return (T) response;
        }
    }



}
