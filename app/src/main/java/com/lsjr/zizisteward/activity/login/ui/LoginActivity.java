package com.lsjr.zizisteward.activity.login.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.activity.login.adapter.LgFragAdapter;
import com.lsjr.zizisteward.activity.login.logfragment.AccountLoginFragment;
import com.lsjr.zizisteward.activity.login.logfragment.CellPhoneLoginFragment;
import com.yangshao.helper.SystemBarHelper;
import com.yangshao.title.NavigationBarView;
import com.yangshao.utils.T_;
import com.yangshao.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/5/11.
 */

public class LoginActivity extends FragmentActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {


    @BindView(R.id.radio0)
    RadioButton rdAccountLg;
    @BindView(R.id.radio1)
    RadioButton rdcellLg;
    @BindView(R.id.all_page)
    ViewPager viewPager;

    LgFragAdapter adapter;
    List<Fragment> fragments = new ArrayList<>();
    @BindView(R.id.radioGroup1)
    RadioGroup gpCheck;

    @BindView( R.id.id_root)
    RelativeLayout rootRL;
    @BindView( R.id.id_bar_view)
    NavigationBarView navigationBarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        SystemBarHelper.tintStatusBar(this, UIUtils.getColor(R.color.colorBlack));
        fragments = new ArrayList<>();
        fragments.add(new AccountLoginFragment());// 主账号登录
        fragments.add(new CellPhoneLoginFragment());// 子账号登录

        adapter = new LgFragAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(fragments.size() - 1);
        viewPager.addOnPageChangeListener(this);
        gpCheck.setOnCheckedChangeListener(this);
        navigationBarView.setLetfIocnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T_.showToastReal("被点击");
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        getTabState(position);
    }

    private void getTabState(int index) {

        rdAccountLg.setChecked(false);
        rdcellLg.setChecked(false);

        switch (index) {
            case 0:
                rdAccountLg.setChecked(true);
                break;
            case 1:
                rdcellLg.setChecked(true);
                break;

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radio0:
                viewPager.setCurrentItem(0);
                break;
            case R.id.radio1:
                viewPager.setCurrentItem(1);
                break;

        }
    }
}
