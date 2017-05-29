package com.yangshao.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yangshao.utils.UIUtils;
import com.yangsho.baselib.R;

/**
 * Created by admin on 2017/5/15.
 */

public class BannerView extends RelativeLayout {


    /* 点点的位置*/
    private int mDoGravity=0;
    private Drawable selectDrawable;
    private Drawable normalDrawable;
    private int mDotSize;
    // 8.自定义属性 - 宽高比例
    private float mWidthProportion;
    private float mHeightProportion;

    /*点的 间距*/
    private int mDotDistance=8;
    private Context mContext;
    private BannerViewPager mBannerVp;
    private TextView mBannerDesc;
    private LinearLayout mDotContainerView;
    private View bannerBottomView;
    // 8.自定义属性 - 底部容器颜色默认透明
    private int mBottomColor = Color.TRANSPARENT;
    private int mCurrentPosition;
    private BannerAdapter mAdapter;

    public BannerView(Context context) {
        this(context,null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context,R.layout.biner_layout,this);
        initAttribute(context,attrs);
        initView();
    }

    private void initAttribute(Context context, AttributeSet attrs) {
        this.mContext=context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerView);
        mDoGravity=typedArray.getInt(R.styleable.BannerView_doGravity,mDoGravity);
        selectDrawable = typedArray.getDrawable(R.styleable.BannerView_dotIndicatorSelect);
        normalDrawable=typedArray.getDrawable(R.styleable.BannerView_dotIndicatorNormal);
        mDotDistance = (int) typedArray.getFloat(R.styleable.BannerView_dotDistance,mDotDistance);
        mDotSize= (int) typedArray.getDimension(R.styleable.BannerView_dotSize, UIUtils.dip2px(mDotSize));
        mWidthProportion = typedArray.getFloat(R.styleable.BannerView_withProportion,mWidthProportion);
        mHeightProportion = typedArray.getFloat(R.styleable.BannerView_heightProportion,mHeightProportion);
        if (selectDrawable==null){
            selectDrawable=new ColorDrawable(Color.RED);
        }
        if (normalDrawable==null){
            normalDrawable=new ColorDrawable(Color.BLACK);
        }
        typedArray.recycle();
    }

    private void initView(){
        mBannerVp = (BannerViewPager) findViewById(R.id.banner_vp);
        mBannerDesc = (TextView) findViewById(R.id.banner_desc_tv);
        mDotContainerView = (LinearLayout) findViewById(R.id.dot_container);
        bannerBottomView = findViewById(R.id.banner_bottom_view);
        bannerBottomView.setBackgroundColor(mBottomColor);
    }

    public void onStartBannerLoop(){
        mBannerVp.startLooper();
    }
    public void onDesotryBannerLoop(){
        mBannerVp.onDestoryLooper();
    }

    public BannerViewPager getmBannerVp() {
        return mBannerVp;
    }

    /*设置适配器*/
    public void setBannerAdapter(BannerAdapter adapter){
        mAdapter=adapter;
        mBannerVp.setBinnerAdapter(mAdapter);
        initDotIndicator();
        mBannerVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                pageSelect(position);
            }
        });
        String firstDesc=mAdapter.getBinnerDesc(0);
        mBannerDesc.setText(firstDesc);
        if (mHeightProportion==0||mWidthProportion==0){
            return;
        }
        post(new Runnable() {
            @Override
            public void run() {
                int width=getMeasuredWidth();  //1080
                int height= (int) (width*mHeightProportion/mWidthProportion);
                getLayoutParams().height=height;
                mBannerVp.getLayoutParams().height=height;
            }
        });
    }


    /*页面切换的回调*/
    private void pageSelect(int position){
        DotIndicatorView oldDotView= (DotIndicatorView) mDotContainerView.getChildAt(mCurrentPosition);
        oldDotView.setImageDrawable(normalDrawable);
        /*改变当前切换位置的点点*/
        mCurrentPosition=position%mAdapter.getCount();
        DotIndicatorView currentDotInView= (DotIndicatorView) mDotContainerView.getChildAt(mCurrentPosition);
        currentDotInView.setImageDrawable(selectDrawable);
        String bannerDesc=mAdapter.getBinnerDesc(mCurrentPosition);
        // 6.3设置广告描述
        mBannerDesc.setText(bannerDesc);
    }


    /**
     * 5.初始化点的指示器
     */
    private void initDotIndicator() {
        // 获取广告的数量
        int count = mAdapter.getCount();
        // 让点的位置在右边
        mDotContainerView.setGravity(getDotGravity());
        mDotContainerView.removeAllViews();
        for (int i = 0; i < count; i++) {
            // 不断的往点的指示器添加圆点
            DotIndicatorView indicatorView = new DotIndicatorView(mContext);
            // 设置大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mDotSize, mDotSize);
            // 设置左右间距
            params.leftMargin = mDotDistance;
            indicatorView.setLayoutParams(params);
            if (i == 0) {
                // 选中位置
                indicatorView.setImageDrawable(selectDrawable);
            } else {
                // 未选中的
                indicatorView.setImageDrawable(normalDrawable);
            }
            mDotContainerView.addView(indicatorView);
        }
    }



    /*开始滚动*/
    private  void startRoll(){
        mBannerVp.startLooper();
    }

    /*获取点的位置*/
    public int getDotGravity(){
        switch (mDoGravity){
            case 0:
                return Gravity.CENTER;
            case 1:
                return Gravity.LEFT;
            case 2:
                return Gravity.RIGHT;

        }
        return Gravity.CENTER;
    }

    public void hidePageIndicator(){
        mDotContainerView.setVisibility(GONE);
    }

    public void showPageIndicator(){
        mDotContainerView.setVisibility(VISIBLE);
    }
}
