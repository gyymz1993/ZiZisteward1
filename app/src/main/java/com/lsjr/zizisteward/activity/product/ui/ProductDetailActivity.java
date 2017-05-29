package com.lsjr.zizisteward.activity.product.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.lsjr.zizisteward.Config;
import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.activity.product.presenter.ProductDetailPresenter;
import com.lsjr.zizisteward.activity.product.view.IProductDetailView;
import com.lsjr.zizisteward.bean.ProductDetail;
import com.lsjr.zizisteward.http.AppUrl;
import com.yangshao.helper.SystemBarHelper;
import com.yangshao.title.NavigationBarView;
import com.yangshao.utils.L_;
import com.yangshao.utils.T_;
import com.yangshao.utils.UIUtils;
import com.ys.lib.base.BaseMvpActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by admin on 2017/5/24.
 */

public class ProductDetailActivity extends BaseMvpActivity<ProductDetailPresenter> implements IProductDetailView {

    //@BindView(R.id.webview)
    WebView wvProductDetail;
    @BindView(R.id.id_nativgation_view)
    NavigationBarView idNativgationView;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar;
    @BindView(R.id.id_web_contain)
    FrameLayout webContain;
    private String mProductId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        super.initView();
        wvProductDetail=new WebView(getApplicationContext());
        Bundle bundle = getIntent().getExtras();
        mProductId = bundle.getString(Config.PRODUCT_ID);
        WebSettings settings = wvProductDetail.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
       // View headerView = View.inflate(this, R.layout.layout_webview, null);
//        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        wvProductDetail = (MyProgressWebView) headerView.findViewById(R.id.web);
//
//        idRoot.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        idRoot.addView(headerView);
        wvProductDetail.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        webContain.addView(wvProductDetail);
        wvProductDetail.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                wvProductDetail.loadUrl(url);
                return true;
            }
        });

        wvProductDetail.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        if (mProductId == null) {
            T_.showToastReal("商品编号有问题");
            return;
        }
        getProductDetailForNet();
    }

    /**
     * 得到商品详细回调
     *
     * @param result
     */
    @SuppressLint({"JavascriptInterface", "AddJavascriptInterface"})
    @Override
    public void onLoadNetDataResult(String result) {
        L_.e("商品详细：" + result);
        ProductDetail productDetail = new Gson().fromJson(result, ProductDetail.class);
        wvProductDetail.loadUrl(AppUrl.Http + productDetail.getProductUrl());
        ProductDetailH5Contrl detailH5Contrl = new ProductDetailH5Contrl();
        wvProductDetail.addJavascriptInterface(detailH5Contrl, Config.H5CONTRL);
    }


    /*
    *下单成功回调 选择支付方式
    * */
    @Override
    public void onSendOrderSucceed(String result) {
        L_.e("下单成功回调 onSendOrderSucceed");

        Bundle bundle = new Bundle();
        bundle.putString(Config.ORDERUTL, sendOrderURL);
        openActivity(PayWayActivity.class, bundle);
    }


    public class ProductDetailH5Contrl {
        public ProductDetailH5Contrl() {
            super();
        }

        /**
         * 和H5方法名 参数类型必须一致
         *
         * @param urls
         */
        @JavascriptInterface
        public void sendUrls(String urls) {
            L_.e("提交订单 sendUrls");
            sendOrderURL = urls;
            sendOrderForNet();
        }

    }

    @Override
    protected void initTitle() {
        super.initTitle();
        SystemBarHelper.tintStatusBar(this, UIUtils.getColor(R.color.colorBlack));
        idNativgationView.setTitleText("商品详情");
    }

    public void getProductDetailForNet() {
        HashMap<String, String> map = new HashMap<>();
        map.put("OPT", "37");
        map.put("sid", mProductId);
        map.put("user_id", Config.USER_ID);
        createPresenter().getProductDetail(map);
    }

    private String sendOrderURL;

    public void sendOrderForNet() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("OPT", "13");
        map.put("user_id", Config.USER_ID);
        createPresenter().senOrder(map);
    }


    @Override
    protected ProductDetailPresenter createPresenter() {
        return new ProductDetailPresenter(this);
    }

    @Override
    protected int loadViewLayout() {
        return R.layout.activity_product_detail;
    }


    @Override
    protected void onDestroy() {
        if (wvProductDetail != null) {
            ViewParent parent = wvProductDetail.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(wvProductDetail);
            }

            wvProductDetail.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            wvProductDetail.getSettings().setJavaScriptEnabled(false);
            wvProductDetail.clearHistory();
            wvProductDetail.clearView();
            wvProductDetail.removeAllViews();
            try {
                wvProductDetail.destroy();
            } catch (Throwable ex) {

            }
        }
        super.onDestroy();

    }
}
