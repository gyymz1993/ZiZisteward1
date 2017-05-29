package com.lsjr.zizisteward.activity.classly.ui;

import android.support.v4.view.ViewPager;

import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.activity.classly.adapter.TitlePagerAdapter;
import com.lsjr.zizisteward.activity.classly.fragment.ClasslyFragment;
import com.lsjr.zizisteward.activity.group.ui.GroupActivity;
import com.yangshao.base.BaseFragment;
import com.yangshao.customview.ColorTrackTabViewIndicator;
import com.yangshao.customview.ColorTrackView;
import com.yangshao.helper.SystemBarHelper;
import com.yangshao.title.NavigationBarView;
import com.yangshao.utils.UIUtils;
import com.ys.lib.base.BaseMvpActivity;
import com.ys.lib.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by admin on 2017/5/13.
 */

public class ClassifyActivity extends BaseMvpActivity implements GroupActivity.GetDataOnclickListener {
    private String[] titles = new String[]{"匠品", "奢品", "出行", "首页", "美食", "健康", "家族"};

    @BindView(R.id.id_bar_view)
    NavigationBarView idBarView;

    @BindView(R.id.tab)
    ColorTrackTabViewIndicator indicatroViewp;
    @BindView(R.id.id_viewpager)
    ViewPager mViewPager;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        List<BaseFragment> fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            ClasslyFragment fragment = new ClasslyFragment();
            fragments.add(fragment);
        }
        indicatroViewp.setTitles(titles, new ColorTrackTabViewIndicator.CorlorTrackTabBack() {
            @Override
            public void onClickButton(Integer position, ColorTrackView colorTrackView) {
                mViewPager.setCurrentItem(position,false);
            }
        });
        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.setAdapter(new TitlePagerAdapter(getSupportFragmentManager(), fragments, titles));
        indicatroViewp.setupViewPager(mViewPager);
    }

    @Override
    protected void initTitle() {
        SystemBarHelper.tintStatusBar(this, UIUtils.getColor(R.color.colorBlack));
    }

    @Override
    protected int loadViewLayout() {
        return R.layout.activity_classly;
    }


    @Override
    public void setData(String data) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
