package com.easeui.home.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;

import com.easeui.home.adapter.HomeAadapter;
import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.bean.AddressBookBean;
import com.yangshao.helper.SystemBarHelper;
import com.yangshao.utils.L_;
import com.yangshao.utils.UIUtils;
import com.ys.lib.base.BaseMvpActivity;
import com.ys.lib.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import april.yun.ISlidingTabStrip;
import april.yun.JPagerSlidingTabStrip2;
import april.yun.other.JTabStyleDelegate;
import butterknife.BindView;

import static april.yun.other.JTabStyleBuilder.STYLE_DEFAULT;

/**
 * 创建人：gyymz1993
 * 创建时间：2017/5/29/3:12
 **/
public class HomeActivity  extends BaseMvpActivity {

    @BindView(R.id.tab_buttom)
    JPagerSlidingTabStrip2 tabBtn;

    @BindView(R.id.pager)
    ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        super.initView();
        setupTabStrips();
        setupViewpager();
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        SystemBarHelper.tintStatusBar(this,UIUtils.getColor(R.color.colorBlack));
    }

//    @Subscribe
//    public void getAddressBookBeanforHome(AddressBookBean addressBookBean) {
//        L_.e("getAddressBookBeanforHome"+addressBookBean.toString());
//    }

    private void setupTabStrips() {
        setupStrip(tabBtn.getTabStyleDelegate(), STYLE_DEFAULT);
        tabBtn.getTabStyleDelegate()
            .setIndicatorHeight(0)
            .setDividerColor(Color.TRANSPARENT);
        tabBtn.getTabStyleDelegate()
            .setFrameColor(Color.TRANSPARENT)
            .setIndicatorColor(Color.TRANSPARENT)
            .setTabIconGravity(Gravity.TOP)//图标显示在top
            .setIndicatorHeight(-8)//设置的高小于0 会显示在tab顶部 否则底部
            .setDividerColor(Color.TRANSPARENT);
    }
    private void setupStrip(JTabStyleDelegate tabStyleDelegate, int type) {
        tabStyleDelegate.setJTabStyle(type)
            .setShouldExpand(true)
            .setFrameColor(Color.parseColor("#45C01A"))
            .setTabTextSize(/*UIUtils.getDimen(13)*/26)
            .setTextColor(Color.parseColor("#45C01A"),Color.GRAY)
            //.setTextColor(R.drawable.tabstripbg)
            .setDividerColor(Color.parseColor("#45C01A"))
            .setDividerPadding(0)
            .setUnderlineColor(Color.parseColor("#3045C01A"))
            .setUnderlineHeight(0)
            .setIndicatorColor(Color.parseColor("#7045C01A"))
            .setIndicatorHeight(/*UIUtils.getDimen(28)*/50);


    }

    private void setupViewpager() {
        int [] mSelectors = new int[] { R.drawable.hy_tab1,
            R.drawable.hy_tab2,
            R.drawable.hy_tab3, R.drawable.hy_tab4 };
        HomeAadapter adapter = new HomeAadapter(getSupportFragmentManager(),mSelectors);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(mSelectors.length);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
            getResources().getDisplayMetrics());
        mViewPager.setPageMargin(pageMargin);
        tabBtn.bindViewPager(mViewPager);
        showPromptMsg(tabBtn);

    }

    public void clearPromptMsg(ISlidingTabStrip slidingTabStrip) {
        for (int i = 0; i < mViewPager.getAdapter().getCount(); i++) {
            slidingTabStrip.setPromptNum(i, 0);
        }
    }


    public void showPromptMsg(ISlidingTabStrip slidingTabStrip) {
        slidingTabStrip.setPromptNum(1, 9).
            setPromptNum(0, 10).
            setPromptNum(2, -9).
            setPromptNum(3, 100);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int loadViewLayout() {
        return R.layout.hy_activity_home;
    }
}
