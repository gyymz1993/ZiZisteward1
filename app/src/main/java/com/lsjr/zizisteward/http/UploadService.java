package com.lsjr.zizisteward.http;

import com.lsjr.zizisteward.utils.EncryptUtils;
import com.ys.lib.base.ApiService;
import com.ys.lib.base.AppClient;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;


public class UploadService {


    private static ApiService getApiService(){
        return AppClient.getApiService();
    }


    /*多图片上传*/
    public static Observable<String> uploadImage(List<File> fileList) {
        String userId = EncryptUtils.addSign(Long.valueOf("77"), "u");
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("user_id", userId)//定义参数key常量类，即参数名
                .addFormDataPart("content", "content")
                .addFormDataPart("customTag", "customTag")
                .addFormDataPart("sightType", "豪车品鉴师")
                .addFormDataPart("type", "1")
                .addFormDataPart("imgNumber", "1");//ParamKey.TOKEN 自定义参数key常量类，即参数名
        //多张图片
        for (int i = 0; i < fileList.size(); i++) {
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), fileList.get(i));
            builder.addFormDataPart("shareImg", fileList.get(i).getName(), imageBody);//"shareImg"+i 后台接收图片流的参数名
        }
        List<MultipartBody.Part> parts = builder.build().parts();
        return getApiService().uploadFiles(parts);
    }


    public void uploadFile(File file){
        String token="";
        RequestBody requestBody=RequestBody.create(MediaType.parse("text/plain"),token);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("shareImg", file.getName(), imageBody);
        getApiService().uploadFile(imageBodyPart,requestBody);
    }



    //网络请求
    public static Observable<String>  retrofit(List<File> files) {
        String userId=EncryptUtils.addSign(Long.valueOf("77"), "u");
        String content="content";
        String sightType="sightType";
        String customTag="customTag";
        String imgNumber="2";
        HttpParameterBuilder httpParameterBuilder = HttpParameterBuilder.newBuilder();
        httpParameterBuilder.addParameter("user_id", userId)
                .addParameter("content", content)
                .addParameter("sightType", sightType)
                .addParameter("customTag", customTag)
                .addParameter("imgNumber", imgNumber);
        //.addParameter("shareImg",files.get(0));
        for (int i=0;i<files.size();i++){
            httpParameterBuilder.addParameter("shareImg",files.get(i));
        }

        Map<String, RequestBody> params=httpParameterBuilder.bulider();
        return getApiService().updateImage(params);
    }

}
