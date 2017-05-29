package com.lsjr.zizisteward.activity.login.presenter;

import com.google.gson.Gson;
import com.hyphenate.DemoHelper;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.db.DemoDBManager;
import com.lsjr.zizisteward.activity.home.ui.HomeActivity;
import com.lsjr.zizisteward.activity.login.view.ILoginView;
import com.lsjr.zizisteward.bean.LoginInfo;
import com.lsjr.zizisteward.http.DcodeService;
import com.lsjr.zizisteward.utils.CustomDialogUtils;
import com.ys.lib.base.BasePresenter;
import com.ys.lib.base.SubscriberCallBack;

import java.util.Map;


public class LoginPresenter extends BasePresenter<ILoginView> {
    public LoginPresenter(ILoginView mvpView) {
        super(mvpView);
    }

    public void getLogin(Map<String, String> map) {

        addSubscription(DcodeService.getServiceData(map), new SubscriberCallBack() {
            @Override
            protected void onError(Exception e) {
                mvpView.onloginError();
            }

            @Override
            protected void onFailure(String response) {
                mvpView.onloginSuccess(response);
            }

            @Override
            protected void onSuccess(String response) {
                mvpView.onloginSuccess(response);
            }

        });
    }


    public void postLogin(String url, Map<String, String> map) {
        addSubscription(DcodeService.postServiceData(url, map), new SubscriberCallBack() {
            @Override
            protected void onError(Exception e) {
                mvpView.onloginSuccess("错误");
            }

            @Override
            protected void onFailure(String response) {
                mvpView.onloginSuccess(response);
            }

            @Override
            protected void onSuccess(String response) {
                mvpView.onloginSuccess(response);
            }


        });
    }


    public void EaseLogin(final String phone, final String password, final String response) {
        DemoDBManager.getInstance().closeDB();
        DemoHelper.getInstance().setCurrentUserName(phone);
        EMClient.getInstance().login(phone, password, new EMCallBack() {
            @SuppressWarnings({"unused", "static-access"})
            @Override
            public void onSuccess() {
                LoginInfo loginInfo = new Gson().fromJson(response, LoginInfo.class);
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                //EMClient.getInstance().updateCurrentUserNick(App.currentUserNick.trim());
                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                //App.setUserInfo(bean);
            }

            @Override
            public void onProgress(int arg0, String arg1) {
            }

            @Override
            public void onError(int arg0, String arg1) {
            }
        });
    }


}
