package com.ys.lib.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yangshao.annotation.InjectUtility;
import com.yangshao.base.AppManager;
import com.yangshao.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static butterknife.ButterKnife.bind;

/**
  * @author: gyymz1993
  * 创建时间：2017/5/3 21:56
  * @version
  *
 **/
public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment {
    protected P mvpPresenter;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (mvpPresenter == null) mvpPresenter = createPresenter();
        super.onViewCreated(view,savedInstanceState);
    }


    protected abstract P createPresenter();


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void bindView(View view) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mvpPresenter = null;
    }



}
