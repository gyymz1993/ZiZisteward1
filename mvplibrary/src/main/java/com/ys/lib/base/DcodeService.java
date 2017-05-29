//package com.ys.lib.base;
//
//import android.text.TextUtils;
//
//import com.ls51.Convert;
//import com.shove.security.Encrypt;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import rx.Observable;
//
//public class DcodeService{
//
//    public static Observable<String> getServiceData(String url,Map map){
//        String  baseUrl=buildUrl(url,"TNNYF17DbevNyxVv",map);
//        return AppClient.getApiService().getData(baseUrl);
//    }
//
//    @SuppressWarnings({ "unchecked", "rawtypes" })
//    private static String buildUrl(String urlBase, String key, Map<String, String> parameters) {
//
//        if ((parameters.containsKey("_s")) || (parameters.containsKey("_t"))) {
//            throw new RuntimeException("在使用 buildUrl 方法构建通用 REST 接口 Url 时，不能使用 _s, _t 此保留字作为参数名");
//        }
//
//        if (TextUtils.isEmpty(key)) {
//            throw new RuntimeException("在使用 buildUrl 方法构建通用 REST 接口 Url 时，必须提供一个用于摘要签名用的 key (俗称 MD5 加盐)");
//        }
//
//        parameters.put("_t", Convert.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss", "1970-01-01 00:00:00"));
//
//        List parameterNames = new ArrayList(parameters.keySet());
//        Collections.sort(parameterNames);
//
//        if ((!urlBase.endsWith("?")) && (!urlBase.endsWith("&"))) {
//            urlBase = urlBase + (urlBase.indexOf("?") == -1 ? "?" : "&");
//        }
//
//        String signData = "";
//        String urlParam = "";
//        for (int i = 0; i < parameters.size(); i++) {
//            String _key = (String) parameterNames.get(i);
//            String _value = (String) parameters.get(_key);
//
//            signData = signData + _key + "=" + _value;
//            try {
//                urlParam = urlParam + _key + "=" + URLEncoder.encode(_value, "utf-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//
//            if (i < parameters.size() - 1) {
//                signData = signData + "&";
//                urlParam = urlParam + "&";
//            }
//        }
//
//        try {
//            urlBase = urlBase + "_t=" + URLEncoder.encode(parameters.get("_t"), "utf-8") + "&_p="
//                    + Encrypt.encrypt3DES(urlParam, key) + "&_s="
//                    + Encrypt.MD5(new StringBuilder(String.valueOf(signData)).append(key).toString(), "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return urlBase;
//    }
//
//
//}
