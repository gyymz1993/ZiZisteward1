package com.lsjr.zizisteward.mvp.presenter;

import com.lsjr.zizisteward.http.UploadService;
import com.lsjr.zizisteward.mvp.view.IUploadView;
import com.yangshao.utils.L_;
import com.ys.lib.base.BasePresenter;
import com.ys.lib.base.SubscriberCallBack;

import java.io.File;
import java.util.List;


public class UploadPresenter extends BasePresenter<IUploadView> {
    public UploadPresenter(IUploadView mvpView) {
        super(mvpView);
    }

    public void onUploadImage( List <File> fileList){
        addSubscription(UploadService.uploadImage(fileList), new SubscriberCallBack() {
            @Override
            protected void onError(Exception e) {
                L_.e("uploadFiles onError");
                mvpView.onUploadSucceed(false);
            }

            @Override
            protected void onFailure(String response) {
                L_.e("uploadFiles");
                mvpView.onUploadSucceed(false);
            }

            @Override
            protected void onSuccess(String response) {
                L_.e("uploadFiles"+response);
                mvpView.onUploadSucceed(true);
            }

        });
    }

}
