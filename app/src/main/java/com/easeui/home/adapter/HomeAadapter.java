package com.easeui.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.easeui.home.fragment.FragmentController;

import april.yun.ISlidingTabStrip;

/**
 * 创建人：gyymz1993
 * 创建时间：2017/5/29/3:32
 **/
public class HomeAadapter  extends FragmentPagerAdapter implements ISlidingTabStrip.IconTabProvider {

    private int [] mSelectors;
    private String[] titles=new String[]{"消息","话题","发现","通讯录"};
    public HomeAadapter(FragmentManager fm,int [] selectors) {
        super(fm);
        this.mSelectors=selectors;
    }


    @Override public CharSequence getPageTitle(int position) {
        return titles[position % 4];
    }


    @Override public int getCount() {
        return mSelectors.length;
    }


    @Override public Fragment getItem(int position) {
        return FragmentController.getInstance().getFragment(position);
    }


    @Override public int[] getPageIconResIds(int position) {
        return null;
    }


    @Override public int getPageIconResId(int position) {
        return mSelectors[position % 4];
    }
}
