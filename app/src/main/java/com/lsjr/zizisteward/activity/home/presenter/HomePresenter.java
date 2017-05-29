package com.lsjr.zizisteward.activity.home.presenter;

import android.content.Intent;

import com.hyphenate.DemoHelper;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.db.DemoDBManager;
import com.lsjr.zizisteward.activity.home.view.IHomeView;
import com.lsjr.zizisteward.bean.LoginInfo;
import com.lsjr.zizisteward.http.DcodeService;
import com.lsjr.zizisteward.utils.CustomDialogUtils;
import com.yangshao.utils.L_;
import com.yangshao.utils.T_;
import com.ys.lib.base.BasePresenter;
import com.ys.lib.base.SubscriberCallBack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/5/16.
 */

public class HomePresenter  extends BasePresenter<IHomeView> {
    public HomePresenter(IHomeView mvpView) {
        super(mvpView);
    }


    /*环信登陆*/
    public void easeLogin(Map<String,String> map){
        addSubscription(DcodeService.getServiceData(map), new SubscriberCallBack() {
            @Override
            protected void onError(Exception e) {
                T_.showToastReal("请求失败");
            }

            @Override
            protected void onFailure(String response) {

            }

            @Override
            protected void onSuccess(String response) {
                mvpView.easeLoignSucceed(response);
            }

        });

    }




    public void getHomePager(Map map){
        addSubscription(DcodeService.getServiceData(map), new SubscriberCallBack() {

            @Override
            protected void onSuccess(String response) {
                L_.e("getHomePager"+response);
                mvpView.getPageDataSucceed(response);
            }


            @Override
            protected void onError(Exception e) {
                L_.e("getHomePager Exception");
            }

            @Override
            protected void onFailure(String response) {
                L_.e("getHomePager"+response);
                mvpView.getPageDataSucceed(response);
            }

        });
    }
}
