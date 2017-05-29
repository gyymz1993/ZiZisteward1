package com.yangshao.banner;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/5/15.
 */

public class BannerViewPager extends ViewPager {

    //自动轮播消息
    private final int MSG=0X11;
    //轮播时间间隔
    private final int MSG_TIME=3500;
    //自动 轮播用Handler控制
    private Handler mHandler;
    //改变ViewPager的速率  自定义页面切换Scroller
    private BinnerScroller mScroller;
    //是否可以滚动
    private boolean mScrollAble=true;

    /*自定义一个BinnerAdapter*/
    private BannerAdapter binnerAdapter;

    /*复用View*/
    private List<View> mConvertViews;


    // 10.内存优化 --> 当前Activity
    private Activity mActivity;

    public BannerViewPager(Context context) {
        this(context,null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);


        mActivity = (Activity) context;
        mConvertViews=new ArrayList<>();
        changeViewPagerScroll(context);
        initHandlerLooper();
    }


    /**
     * 2.销毁Handler停止发送  解决内存泄漏
     */

    public void onDestoryLooper() {
        if (mHandler != null) {
            // 销毁Handler的生命周期
            mHandler.removeMessages(MSG);
            // 解除绑定
            mHandler = null;
        }
    }

    public void onAttachedToWindow() {
        if (binnerAdapter != null) {
            initHandlerLooper();
            startLooper();
        }
    }


    /**
     * 1.设置自定义的BannerAdapter
     */
    public void setBinnerAdapter(BannerAdapter adapter) {
        this.binnerAdapter = adapter;
        // 设置父类 ViewPager的adapter
        setAdapter(new BinnerPagerAdapter());
    }


    /*页面切换动画持续时间*/
    public void setScrollerDuration(int scrollerDuration){
        mScroller.setmScrollerDuration(scrollerDuration);
    }


    private void initHandlerLooper() {
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                /*每隔多少秒切换一次*/
                setCurrentItem(getCurrentItem()+1);
                startLooper();
            }
        };
    }
    public void startLooper() {
        if (binnerAdapter==null)return;
                /*不是只有一条数据*/
        mScrollAble= binnerAdapter.getCount() != 1;
        if (mScrollAble&&mHandler!=null){
            mHandler.removeMessages(MSG);
            mHandler.sendEmptyMessageDelayed(MSG,MSG_TIME);
        }
    }

    private void changeViewPagerScroll(Context context) {
        try {
            Field filed=ViewPager.class.getDeclaredField("mScroller");
            filed.setAccessible(true);
            mScroller=new BinnerScroller(context);
            filed.set(true,mHandler);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    class BinnerScroller extends Scroller{
        // 3.改变ViewPager切换的速率 - 动画持续的时间
        private int mScrollerDuration = 950;

        public BinnerScroller(Context context) {
            super(context);
        }

        public BinnerScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public BinnerScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        public void setmScrollerDuration(int mScrollerDuration) {
            this.mScrollerDuration = mScrollerDuration;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy,mScrollerDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mScrollerDuration);
        }
    }

    class BinnerPagerAdapter extends PagerAdapter{
        /*实现无线循环*/
        @Override
        public int getCount() {
            return binnerAdapter.isCircularSliding()?Integer.MAX_VALUE:binnerAdapter.getCount();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            // 官方推荐这么写  源码
            return view == object;
        }

        /* 创建Viewpager条目的回调方法*/

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View bannerItemView = binnerAdapter.getView(position % binnerAdapter.getCount(), getConvertView());
            container.addView(bannerItemView);
            bannerItemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (binnerOnClick!=null){
                        binnerOnClick.binnerOnclick(position % binnerAdapter.getCount());
                    }
                }
            });
            return bannerItemView;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
            mConvertViews.add((View) object);
        }

        /*页面复用 */
        public View getConvertView() {
            for (View convertView:mConvertViews){
                if (convertView.getParent()==null){
                    return convertView;
                }
            }
            return null;
        }

    }


    // 管理Activity的生命周期
    private Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks =
            new DefaultActivityLifecycleCallbacks() {
                @Override
                public void onActivityResumed(Activity activity) {
                    // 是不是监听的当前Activity的生命周期
                    // Log.e("TAG", "activity --> " + activity + "  context-->" + getContext());
                    if (activity == mActivity) {
                        // 开启轮播
                        startLooper();
                        // mHandler.sendEmptyMessageDelayed(mCutDownTime, SCROLL_MSG);
                    }
                }

                @Override
                public void onActivityPaused(Activity activity) {
                    if (activity == mActivity) {
                        // 停止轮播
                        mHandler.removeMessages(MSG);
                    }
                }
            };


    BinnerItemOnClickListener binnerOnClick;
    public interface BinnerItemOnClickListener{
        public void binnerOnclick(int position);
    }
}
