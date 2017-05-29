package com.easeui.home.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsjr.zizisteward.R;
import com.ys.lib.base.BaseMvpFragment;
import com.ys.lib.base.BasePresenter;

/**
 * Created by admin on 2017/5/11.
 */

public class FindFragment extends BaseMvpFragment {
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


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
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup viewGrop) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_find,null);
    }

    @Override
    protected void bindView(View view) {

    }


}
