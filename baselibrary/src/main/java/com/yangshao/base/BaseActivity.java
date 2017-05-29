package com.yangshao.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yangshao.Notice;
import com.yangshao.annotation.InjectUtility;
import com.yangshao.utils.L_;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;

public abstract class BaseActivity extends AppCompatActivity  {


    protected Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 一些特定的算法，子类基本都会使用的
        InjectUtility.initInjectedView(this);
        AppManager.getAppManager().addActivity(this);
        // 设置布局layout
        setContentView(loadViewLayout());

        unbinder= ButterKnife.bind(this);
        // 初始化界面
        initView();

        // 初始化头部
        initTitle();

        // 初始化数据
        initData();


    }


    // 初始化数据
    protected abstract void initData();

    // 初始化界面
    protected abstract void initView();

    // 初始化头部
    protected abstract void initTitle();

    // 设置布局layout
    protected abstract int loadViewLayout();


    public void openActivity(Class<?> pClass, Bundle bundle){
        Intent intent=new Intent(this,pClass);
        if (bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void openActivity(Class<?> pClass){
        Intent intent=new Intent(this,pClass);
        startActivity(intent);
    }

    /*跳转到登录页面  登录成功回调到刚刚页面*/
    public void loginToServer(Class<?> c,Activity resultActivcity) {
        Intent loginIntent = new Intent(this,resultActivcity.getClass());
        loginIntent.putExtra("", c);
        startActivity(loginIntent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        if (unbinder != Unbinder.EMPTY) unbinder.unbind();
        unbinder=null;
    }

    /**
     * 注册事件通知
     */
//    public Observable<Notice> toObservable() {
//        return RxBus.getDefault().toObservable(Notice.class);
//        EventBus.getDefault().register(this);
//    }
//
//    /**
//     * 发送消息
//     */
//    public void post(Notice msg) {
//        RxBus.getDefault().post(msg);
//    }

}
