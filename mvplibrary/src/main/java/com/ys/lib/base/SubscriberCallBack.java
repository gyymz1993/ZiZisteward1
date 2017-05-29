package com.ys.lib.base;

import com.yangshao.utils.L_;
import com.yangshao.utils.T_;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import rx.exceptions.OnErrorFailedException;

public abstract class SubscriberCallBack extends BaseCallBack<String> {
    final String ERROR = "error";
    final String MSG = "msg";
    @Override
    public void onNext(String response) {
        L_.e("网络请求成功"+response);
        response = response.replace("null", "\"\"");
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (!jsonObject.isNull(ERROR)) {
                String error = jsonObject.getString(ERROR);
                String msg = jsonObject.getString(MSG);
                if (error.equals("1")) {
                    if (response != null) {
                        onSuccess(response);
                        L_.e("网络请求成功并返回"+response);
                    } else {
                        T_.showToastReal("result value is empty!");
                        onFailure(response);
                    }
                } else {
                    onFailure(msg);
                    T_.showToastReal("result value is empty!");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            onError(e);
        }
    }

    @Override
    public void onError(final Throwable e) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (e instanceof SocketTimeoutException) {
                    T_.showToastReal("网络连接超时");
                } else if (e instanceof SocketException) {
                    if (e instanceof ConnectException) {
                        T_.showToastReal("网络未连接");
                    } else {
                        T_.showToastReal("网络错误");
                    }
                }
                T_.showToastReal("异常");
                onError(new OnErrorFailedException(e));
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    protected abstract void onError(Exception e) ;

    protected abstract void  onFailure(String response);

    protected abstract void onSuccess(String response);



}
