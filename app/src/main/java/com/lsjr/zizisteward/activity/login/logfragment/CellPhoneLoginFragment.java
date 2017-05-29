package com.lsjr.zizisteward.activity.login.logfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.activity.login.ui.ThirdPartActivity;
import com.ys.lib.base.BaseMvpFragment;
import com.ys.lib.base.BasePresenter;

/**
 * Created by admin on 2017/5/11.
 */

public class CellPhoneLoginFragment  extends BaseMvpFragment {


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        openActivity(ThirdPartActivity.class);
    }

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup viewGrop) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_phone_login, null);
    }

    @Override
    protected void bindView(View view) {

    }

}
