package com.lsjr.zizisteward.coustom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lsjr.zizisteward.R;
import com.ys.lib.anythingpull.AnythingPullLayout;
import com.ys.lib.anythingpull.IRefresh;

/**
 * 下拉刷新
 */
public class RefreshView extends LinearLayout implements IRefresh {
    private TextView tvStatus;
    private ImageView imageView;
    private RotateAnimation rotateAnimation;

    public RefreshView(Context context) {
        this(context, null);
    }

    public RefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.common_layout, this);
        tvStatus = (TextView) findViewById(R.id.tv_status);
        imageView = (ImageView) findViewById(R.id.image);
    }

    private void startAnim() {
        rotateAnimation = new RotateAnimation(0, 360,
                imageView.getMeasuredWidth() >> 1, imageView.getMeasuredHeight() >> 1);
        rotateAnimation.setDuration(750);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        imageView.startAnimation(rotateAnimation);
    }

    @Override
    public void preShow() {
        imageView.setVisibility(VISIBLE);
        tvStatus.setText("下拉刷新");
    }

    @Override
    public void preDismiss() {
        tvStatus.setText("下拉刷新");
    }

    @Override
    public void onDismiss() {
        imageView.clearAnimation();
        imageView.setVisibility(GONE);
    }

    @Override
    public void onPositionChange(boolean touch, int distance, @AnythingPullLayout.Status int status) {
        if (status != AnythingPullLayout.REFRESH_ING && status != AnythingPullLayout.REFRESH_RESULT) {
            imageView.setRotation(distance);
        }
        if (status == AnythingPullLayout.TO_REFRESH) {
            tvStatus.setText("释放立即刷新");
        } else if (status == AnythingPullLayout.PRE_REFRESH) {
            tvStatus.setText("下拉刷新");
        }
    }


    @Override
    public void onRefreshStart() {
        startAnim();
        tvStatus.setText("正在刷新...");
    }

    @Override
    public void onRefreshFinish(boolean success) {
        imageView.clearAnimation();
        imageView.setVisibility(GONE);
        tvStatus.setText(success ? "刷新成功" : "刷新失败");
    }
}
