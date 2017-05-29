package com.lsjr.zizisteward.activity.product.presenter;

import com.lsjr.zizisteward.activity.product.view.IProductListView;
import com.lsjr.zizisteward.http.DcodeService;
import com.ys.lib.base.BasePresenter;
import com.ys.lib.base.SubscriberCallBack;

import java.util.Map;

/**
 * Created by admin on 2017/5/24.
 */

public class ProductListPresenter extends BasePresenter<IProductListView> {
    public ProductListPresenter(IProductListView mvpView) {
        super(mvpView);
    }

    public void getProductList(Map  map){
        addSubscription(DcodeService.getServiceData(map), new SubscriberCallBack() {
            @Override
            protected void onError(Exception e) {
            }

            @Override
            protected void onFailure(String response) {

            }

            @Override
            protected void onSuccess(String response) {
                mvpView.onLoadNetDataResult(response);
            }
        });
    }
}
