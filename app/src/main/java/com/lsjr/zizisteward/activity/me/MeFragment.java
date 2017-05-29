package com.lsjr.zizisteward.activity.me;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsjr.zizisteward.R;
import com.ys.lib.base.BaseMvpFragment;
import com.ys.lib.base.BasePresenter;

/**
 * Created by admin on 2017/5/8.
 */

public class MeFragment extends BaseMvpFragment {


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
        return  LayoutInflater.from(getActivity()).inflate(R.layout.fragment_me, null);
    }

    @Override
    protected void bindView(View view) {

    }


}
