package com.yangshao.banner;

import android.view.View;

/**
 * Created by admin on 2017/5/15.
 */

public abstract class BannerAdapter {

    /*根据位置获取Viewpager中的子View*/
    public abstract View getView(int position,View converView);

    /*轮播数量*/
    public abstract int getCount();

    /*根据位置获取广告的描述*/
    public String getBinnerDesc(int position){
        return "";
    }


    /*是否循环滑动*/
    public abstract  boolean isCircularSliding();
}
