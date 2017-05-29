package com.easeui.home.presenter;

import com.easeui.home.view.IAddressListView;
import com.lsjr.zizisteward.http.DcodeService;
import com.ys.lib.base.BasePresenter;
import com.ys.lib.base.SubscriberCallBack;

import java.util.Map;

/**
 * 创建人：gyymz1993
 * 创建时间：2017/5/29/13:22
 **/
public class AddressListPresenter extends BasePresenter<IAddressListView> {
    public AddressListPresenter(IAddressListView mvpView) {
        super(mvpView);
    }

    public void loadAdressListforNet(Map map){
        addSubscription(DcodeService.getServiceData(map), new SubscriberCallBack() {
            @Override
            protected void onError(Exception e) {

            }

            @Override
            protected void onFailure(String response) {

            }

            @Override
            protected void onSuccess(String response) {
                mvpView.onLoadAdressResult(response);
            }
        });
    }
}
