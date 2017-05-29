package com.lsjr.zizisteward.coustom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lsjr.zizisteward.R;
import com.yangshao.utils.NetUtils;
import com.yangshao.utils.UIUtils;

/**
 * Created by admin on 2017/5/24.
 */

public class MyProgressWebView extends WebView {

    /**
     * 网页缓存目录
     */
    private static final String cacheDirPath = Environment
            .getExternalStorageDirectory() + "/LoadingWebViewDome/webCache/";
    public MyProgressWebView(Context context) {
        this(context,null);
    }

    public MyProgressWebView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyProgressWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addPorgress(context);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initView(){
        requestFocus();
        setInitialScale(39);
        WebSettings settings = getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过Javascript打开新窗口
        settings.setJavaScriptEnabled(true);//设置WebView属性，能够执行Javascript脚本
        settings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        settings.setDomStorageEnabled(true);//设置是否启用了DOM Storage API
        settings.setDatabaseEnabled(true);//开启database storage API功能
        settings.setDatabasePath(cacheDirPath); //设置数据库缓存路径
        settings.setAppCachePath(cacheDirPath);//设置Application Caches缓存目录
        settings.setAppCacheEnabled(true);//开启Application Caches功能
    }

    public void loadMessageUrl(String url){
        super.loadUrl(url);
        setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //重写此方法表明点击网页里面的链接不调用系统浏览器，而是在本WebView中显示
                loadUrl(url);//加载需要显示的网页
                return true;
            }
        });
    }
    
    @SuppressLint("SetJavaScriptEnabled")
    ProgressBar progressBar;
    public void  addPorgress(Context context){
        progressBar=new ProgressBar(context);
        progressBar.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,5,0,0));
        Drawable  drawable= UIUtils.getDrawble(R.drawable.web_progressbar_bg);
        progressBar.setProgressDrawable(drawable);
        addView(progressBar);
        setWebChromeClient(new WebChromeClient());

    }


    public void destroyWebView() {
        clearCache(true);
        clearHistory();
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressBar.setVisibility(GONE);
            } else {
                if (progressBar.getVisibility() == GONE)
                    progressBar.setVisibility(VISIBLE);
                progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }


    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressBar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressBar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
