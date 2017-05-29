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

import com.yangshao.utils.L_;
import com.yangsho.baselib.R;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.x;


public class ColorTrackTabViewIndicator extends HorizontalScrollView {

    private Context mContext;
    private int mTabTextSize;
    private int mTabSelectedTextColor;
    private Paint mPaint = new Paint();
    private int mIndicatorColor;
    private String[] mTitles;
    private int mTabCount;
    private int mTabTextColor;
    private CorlorTrackTabBack icallBack;
    private List<ColorTrackView> viewList = new ArrayList<ColorTrackView>();
    private float mTranslationX;
    private int mTabWidth;

    public int getTabWidth() {
        return mTabWidth;
    }

    public static final int MODE_SCROLLABLE = 0;

    public static final int MODE_FIXED = 1;
    private int mMode;
    private LinearLayout mTabStrip;

    public ColorTrackTabViewIndicator(Context context, AttributeSet attrs,
                                      int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.ColorTrackTabViewIndicator);

        mIndicatorColor = ta.getColor(R.styleable.ColorTrackTabViewIndicator_ctTabIndicatorColor, -1);

        mTabTextColor = ta.getColor(R.styleable.ColorTrackTabViewIndicator_ctTabTextColor, Color.YELLOW);

        mTabSelectedTextColor = ta.getColor(R.styleable.ColorTrackTabViewIndicator_ctTabSelectedTextColor, Color.YELLOW);

        mTabTextSize = (int) ta.getDimension(R.styleable.ColorTrackTabViewIndicator_ctTabTextSize, sp2px(getContext(), 14));


        mTabWidth = (int) ta.getDimension(R.styleable.ColorTrackTabViewIndicator_ctTabWidth, -1);
        mMode = ta.getInt(R.styleable.ColorTrackTabViewIndicator_ctMode, MODE_FIXED);
        ta.recycle();
        if (mIndicatorColor != -1) {
            this.mPaint.setColor(mIndicatorColor);
            this.mPaint.setStrokeWidth(2.0F);
        }
        mTabStrip = new LinearLayout(context);
        mTabStrip.setOrientation(LinearLayout.HORIZONTAL);
        mTabStrip.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mTabStrip);
    }

    public ColorTrackTabViewIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTabViewIndicator(Context context) {
        this(context, null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.mTabCount != 0 && mTabWidth == -1) {
            this.mTabWidth = (w / this.mTabCount);
        }
    }

    public void setupViewPager(ViewPager vp) {
        if (vp == null) return;
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int prePostiion = -1;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabScrolled(position, positionOffset);
                int x = calculateScrollXForTab(position, positionOffset);
                smoothScrollTo(x, 0);
            }

            @Override
            public void onPageSelected(int position) {
                if (prePostiion != -1) {
                    ColorTrackView colorTrackView = viewList.get(prePostiion);
                    colorTrackView.setProgress(0f);
                }
                prePostiion = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private int calculateScrollXForTab(int position, float positionOffset) {
        if (mMode == MODE_SCROLLABLE) {
            final View selectedChild = mTabStrip.getChildAt(position);
            final View nextChild = position + 1 < mTabStrip.getChildCount()
                    ? mTabStrip.getChildAt(position + 1)
                    : null;
            final int selectedWidth = selectedChild != null ? selectedChild.getWidth() : 0;
            final int nextWidth = nextChild != null ? nextChild.getWidth() : 0;

            return selectedChild.getLeft()
                    + ((int) ((selectedWidth + nextWidth) * positionOffset * 0.5f))
                    + (selectedChild.getWidth() / 2)
                    - (getWidth() / 2);
        }
        return 0;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mIndicatorColor != -1) {
            canvas.save();
            canvas.translate(mTranslationX, getHeight() - 2);
            // canvas.drawLine(0.0F, 0.0F, mTabWidth, 0.0F, this.mPaint);
            canvas.restore();
        }
    }

    public void tabScrolled(int position, float positionOffset) {
        L_.e("getWidth() :" + getWidth() + "    this.mTabCount " + this.mTabCount);
        L_.e("position :" + position + "    positionOffset " + positionOffset);
        this.mTranslationX = (getWidth() / this.mTabCount * (position + positionOffset));
        if (positionOffset == 0.0F) {
            return;
        }
        ColorTrackView currentTrackView = (ColorTrackView) this.viewList
                .get(position);
        ColorTrackView nextTrackView = (ColorTrackView) this.viewList
                .get(position + 1);
        currentTrackView.setDirection(1);
        currentTrackView.setProgress(1.0F - positionOffset);
        preColorTrackView = currentTrackView;
        nextTrackView.setDirection(0);
        nextTrackView.setProgress(positionOffset);
        invalidate();
    }

    public void tabSelected(int position) {
        ((ColorTrackView) viewList.get(position)).setProgress(1.0F);
    }

    public void setTitles(String[] titles, CorlorTrackTabBack callBack) {
        this.mTitles = titles;
        this.mTabCount = titles.length;
        this.icallBack = callBack;
        generateTitleView();
    }

    private ColorTrackView preColorTrackView;

    private void generateTitleView() {
        if (mTabStrip.getChildCount() > 0) {
            mTabStrip.removeAllViews();
        }
        for (int i = 0; i < mTitles.length; i++) {
            LinearLayout tabLayout = new LinearLayout(getContext());
            tabLayout.setOrientation(LinearLayout.HORIZONTAL);
            tabLayout.setGravity(Gravity.CENTER);
            tabLayout.setTag(Integer.valueOf(i));
            tabLayout.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (preColorTrackView != null) {
                        preColorTrackView.setProgress(0F);
                    }
                    int index = (Integer) v.getTag();
                    ColorTrackView colorTrackView = (ColorTrackView) ((LinearLayout) mTabStrip.getChildAt(index))
                            .getChildAt(0);
                    colorTrackView.setProgress(1F);
                    if (icallBack != null) {
                        icallBack.onClickButton(index, colorTrackView);
                    }
                    preColorTrackView = colorTrackView;
                    mTranslationX = (getWidth() / mTabCount * index);
                    invalidate();

                }
            });

            ColorTrackView colorTrackView = new ColorTrackView(getContext());
            LayoutParams params = null;
            if (mMode == MODE_FIXED) {
                int widthPixels = mContext.getResources().getDisplayMetrics().widthPixels;
                params = new LayoutParams(mTabWidth == -1 ? widthPixels / mTitles.length : mTabWidth, -2);
            } else {
                params = new LayoutParams(mTabWidth == -1 ? -2 : mTabWidth, -2);
            }

            colorTrackView.setLayoutParams(params);
            colorTrackView.setTag(Integer.valueOf(i));
            colorTrackView.setText(this.mTitles[i]);
            colorTrackView.setTextOriginColor(mTabTextColor);
            colorTrackView.setTextChangeColor(mTabSelectedTextColor);
            colorTrackView.setTextSize(mTabTextSize);
            if (i == 0) {
                colorTrackView.setProgress(1.0F);
                preColorTrackView = colorTrackView;
            }
            tabLayout.addView(colorTrackView);
            viewList.add(colorTrackView);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            tabLayout.setLayoutParams(layoutParams);
            mTabStrip.addView(tabLayout);
        }
    }


    public interface CorlorTrackTabBack {
        void onClickButton(Integer position, ColorTrackView colorTrackView);
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

    public ColorTrackView getTabAt(int position) {
        return viewList.get(position);
    }
}
