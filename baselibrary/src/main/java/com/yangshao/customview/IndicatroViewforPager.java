package com.yangshao.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yangshao.utils.UIUtils;
import com.yangsho.baselib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/5/12.
 */

public class IndicatroViewforPager extends HorizontalScrollView{
    private int mTabTextSize;
    private int mTabSelectTextColor;
    private Paint mPaint;
    private int mIndicatorColor;
    private String [] mTitles;
    private int mTabCount;
    private int mTabTextColor;
    private float mTranslationX;
    private int mTabWidth;
    private int mAttrTextColor;
    private int mAttrSelectedTextColor;
    private int mAttBackground;
    private List<TextView> viewList = new ArrayList<TextView>();
    private LinearLayout mTabStrip;
    private int mMode;
    public IndicatroViewforPager(Context context) {
        this(context,null);
    }

    public IndicatroViewforPager(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IndicatroViewforPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackIndicatorView);
        mIndicatorColor = ta.getColor(R.styleable.ColorTrackIndicatorView_indicatorColor, -1);
        mTabTextColor = ta.getColor(R.styleable.ColorTrackIndicatorView_textColor, Color.YELLOW);
        mTabSelectTextColor = ta.getColor(R.styleable.ColorTrackIndicatorView_selectedTextColor, Color.YELLOW);
        mTabTextSize = (int) ta.getDimension(R.styleable.ColorTrackIndicatorView_textSize, sp2px(getContext(), 8));
        mTabWidth = (int) ta.getDimension(R.styleable.ColorTrackIndicatorView_tabWidth, -1);
        mMode=ta.getInt(R.styleable.ColorTrackIndicatorView_mode,0);
        ta.recycle();
        if (mIndicatorColor!=-1){
            mPaint.setColor(mIndicatorColor);
            mPaint.setStrokeWidth(1);
        }
        mTabStrip=new LinearLayout(context);
        mTabStrip.setOrientation(LinearLayout.HORIZONTAL);
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mTabStrip.setLayoutParams(layoutParams);
        addView(mTabStrip);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
       /*根据个数计算宽度*/
        if (this.mTabCount!=0&&mTabWidth==-1){
            this.mTabWidth=w/mTabCount;
        }
    }

    public void setViewPager(ViewPager viewPager){
        if (viewPager==null)return;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int mPosition=-1;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabScrolled(position,positionOffset);
                View tabView=mTabStrip.getChildAt(position);
                int x=calculateScrollXforTab(position,positionOffset);
                smoothScrollTo(x,0);
            }

            private int calculateScrollXforTab(int position, float positionOffset) {
                if (mMode==0){
                    final View selectedChild=mTabStrip.getChildAt(position);
                    final View nextChild=position+1<mTabStrip.getChildCount()?
                            mTabStrip.getChildAt(position+1):null;
                    final int selectedWidth=selectedChild!=null?selectedChild.getWidth():0;
                    final int nextWidht=nextChild!=null?nextChild.getWidth():0;
                    return selectedChild.getLeft()+(int)((selectedWidth+nextWidht)
                            *positionOffset*0.5f)+ ((selectedChild.getWidth()+getWidth())/2);
                }
                return 0;
            }

            private void tabScrolled(int position, float positionOffset) {
                mTranslationX=(getWidth()/mTabCount*(position+positionOffset));
                if(positionOffset==0.0F){
                    return;
                }
                TextView currentTv=viewList.get(position);
                TextView nextTv=viewList.get(position+1);
                invalidate();
            }

            @Override
            public void onPageSelected(int position) {
                if (position!=-1){

                }
                mPosition=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mIndicatorColor!=-1){
            canvas.save();
            canvas.translate(mTranslationX,getHeight()/2);
            canvas.drawLine(0.0F,0.0F,mTabWidth,0.0F,mPaint);
            canvas.restore();
        }
    }

    private void tabSelected(int position){
        viewList.get(position);
    }

    public void setTitles(String [] titles){
        this.mTitles=titles;
        this.mTabCount=titles.length;
        generateTitleView();
    }

    private TextView preColorTrackView;
    private void generateTitleView() {
        if (mTabStrip.getChildCount()>0){
            mTabStrip.removeAllViews();
        }
        for (int i=0;i<mTitles.length;i++){
            LinearLayout tabLayout=new LinearLayout(getContext());
            tabLayout.setOrientation(LinearLayout.HORIZONTAL);
            tabLayout.setGravity(Gravity.CENTER);
            tabLayout.setTag(Integer.valueOf(i));
            tabLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (preColorTrackView!=null){
                    }
                    int index= (int) view.getTag();
                    LinearLayout ll= (LinearLayout) mTabStrip.getChildAt(index);
                    TextView textView= (TextView) ll.getChildAt(0);
                    if (icallBack!=null){
                        icallBack.onClickButton(index,textView);
                    }
                    preColorTrackView=textView;
                    mTranslationX=getWidth()/mTabCount*index;
                    invalidate();
                }
            });

            TextView colorTrackView=new TextView(getContext());
            ViewGroup.LayoutParams params=null;
            if (mMode==1){
                int widthPixels=getContext().getResources().getDisplayMetrics().widthPixels;
                params=new ViewGroup.LayoutParams(mTabWidth!=-1?widthPixels/mTitles.length:mTabWidth,-2);
            }else {
                params=new ViewGroup.LayoutParams(mTabWidth == -1 ? -2 : mTabWidth, -2);
            }
            colorTrackView
                    .setLayoutParams(params);
            colorTrackView.setTag(Integer.valueOf(i));
            colorTrackView.setText(this.mTitles[i]);
            colorTrackView.setTextSize(UIUtils.dp2px(8));
            if (i == 0) {
                preColorTrackView = colorTrackView;
            }
            tabLayout.addView(colorTrackView);
            viewList.add(colorTrackView);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            tabLayout.setLayoutParams(layoutParams);
            mTabStrip.addView(tabLayout);
        }
    }

    CorlorTrackTabBack icallBack;
    public interface CorlorTrackTabBack {
        void onClickButton(Integer position, TextView colorTrackView);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
