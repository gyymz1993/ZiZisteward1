package com.lsjr.zizisteward.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.ls51.Convert;
import com.lsjr.zizisteward.bean.HomeBean;
import com.shove.gateway.weixin.gongzhong.vo.user.UserInfo;
import com.shove.security.Encrypt;
import com.yangshao.utils.L_;
import com.ys.lib.base.ApiService;
import com.ys.lib.base.AppClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

import static com.lsjr.zizisteward.http.AppUrl.baseUrl;

public class DcodeService {

    private static ApiService getApiService(){
        return AppClient.getApiService();
    }

    /* get网络请求入口*/
    public static Observable<String> getServiceData(String url,Map map){
        String baseUrl=encryptUrl(url,AppUrl.URLKEY,map);
        return getApiService().getData(baseUrl);
    }

    /* get网络请求入口*/
    public static Observable<String> getServiceData(Map map){
        spliceGetUrl(map);
        String baseUrl=encryptUrl(AppUrl.HOST,AppUrl.URLKEY,map);
        return getApiService().getData(baseUrl);
    }


    /* get网络请求入口*/
    public static Observable<String> postServiceData(Map map){
        String baseUrl=encryptUrl(AppUrl.HOST,AppUrl.URLKEY,map);
        return getApiService().postData(baseUrl);
    }


    /* post网络请求入口*/
    public static Observable<String> postServiceData(String url,Map map){
        String baseUrl=encryptUrl(url,AppUrl.URLKEY,map);
        return getApiService().postData(baseUrl);
    }


    //可以传所有 实体类转 Json数据
    public Observable<String> getRequsetJson() {
        return getApiService().requsetJson(getRequestBody(new UserInfo()));
    }


    /*没有加密*/
    public static Observable<String> loadData(String url,Map map){
        return getApiService().loadData(url,map);
    }


    /*实体类转Json传输*/
    private RequestBody getRequestBody(Object obj) {
        String route = new Gson().toJson(obj);
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),route);
        return body;
    }


    /* Get 参数拼接 */
    private static void spliceGetUrl(Map mParams) {
        if (mParams != null && mParams.size() > 0) {
            String url = AppUrl.HOST;
            if (TextUtils.isEmpty(url)) {
                return;
            }
            if (url != null && !url.contains("?")) {
                url += "?";
            }
            String param = "";
            for (Object key : mParams.keySet()) {
                param += (key + "=" + mParams.get(key) + "&");
            }
            param = param.substring(0, param.length() - 1);// 去掉最后一个&
            L_.e("没有加密的URL"+url + param);
        }
    }

    /**
     * Url加密处理
     * @param urlBase
     * @param key
     * @param parameters
     * @return
     */
    private static String encryptUrl(String urlBase, String key, Map<String, String> parameters) {
        if ((parameters.containsKey("_s")) || (parameters.containsKey("_t"))) {
            throw new RuntimeException("在使用 buildUrl 方法构建通用 REST 接口 Url 时，不能使用 _s, _t 此保留字作为参数名");
        }

        if (TextUtils.isEmpty(key)) {
            throw new RuntimeException("在使用 buildUrl 方法构建通用 REST 接口 Url 时，必须提供一个用于摘要签名用的 key (俗称 MD5 加盐)");
        }
        parameters.put("_t", Convert.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss", "1970-01-01 00:00:00"));
        List parameterNames = new ArrayList(parameters.keySet());
        Collections.sort(parameterNames);

        if ((!urlBase.endsWith("?")) && (!urlBase.endsWith("&"))) {
            urlBase = urlBase + (urlBase.indexOf("?") == -1 ? "?" : "&");
        }
        String signData = "";
        String urlParam = "";
        for (int i = 0; i < parameters.size(); i++) {
            String _key = (String) parameterNames.get(i);
            String _value = (String) parameters.get(_key);

            signData = signData + _key + "=" + _value;
            try {
                urlParam = urlParam + _key + "=" + URLEncoder.encode(_value, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (i < parameters.size() - 1) {
                signData = signData + "&";
                urlParam = urlParam + "&";
            }
        }
        try {
            urlBase = urlBase + "_t=" + URLEncoder.encode(parameters.get("_t"), "utf-8") + "&_p="
                    + Encrypt.encrypt3DES(urlParam, key) + "&_s="
                    + Encrypt.MD5(new StringBuilder(String.valueOf(signData)).append(key).toString(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return urlBase;
    }


}
