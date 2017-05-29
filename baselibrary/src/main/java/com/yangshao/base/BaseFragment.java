package com.yangshao.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yangshao.annotation.InjectUtility;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/3/14.
 */

public abstract class BaseFragment extends Fragment {

    protected boolean isFirstCreate = true;
    protected View rootView;
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return loadViewLayout(inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InjectUtility.initInjectedView(this, view);
        mUnbinder = ButterKnife.bind(this, view);
        rootView = view;
        //绑定View
        bindView(view);

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onInvisible();
        } else {
            onVisible();
        }
    }

    protected void onVisible() {
        if (isFirstCreate) {
            lazyLoad();
            isFirstCreate = false;
        }
    }

    public void openActivity(Class<?> pClass, Bundle bundle) {
        Intent intent = new Intent(getActivity(), pClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void openActivity(Class<?> pClass) {
        Intent intent = new Intent(getActivity(), pClass);
        startActivity(intent);
    }


    protected void lazyLoad() {
    }

    protected void onInvisible() {
    }

    protected abstract View loadViewLayout(LayoutInflater inflater, ViewGroup viewGrop);

    protected abstract void bindView(View view);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != Unbinder.EMPTY&&mUnbinder!=null) mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.rootView = null;
        this.mUnbinder = null;
    }
}
