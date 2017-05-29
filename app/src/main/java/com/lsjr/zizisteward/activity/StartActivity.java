package com.lsjr.zizisteward.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.activity.login.ui.LoginActivity;
import com.yangshao.helper.SystemBarHelper;
import com.yangshao.utils.L_;
import com.yangshao.utils.UIUtils;
import com.ys.lib.base.BaseMvpActivity;
import com.ys.lib.base.BasePresenter;

import butterknife.BindView;

/**
 * Created by admin on 2017/5/11.
 */

public class StartActivity extends BaseMvpActivity {

    @BindView(R.id.splash)
    ImageView igSplash;
    @BindView(R.id.yangben)
    TextView tvUpdate;
    private static final int ANIMATION_DURATION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    public void playAnimation(){
        AnimationSet animationSet=new AnimationSet(true);
        /*缩放动画*/
        ScaleAnimation scaleAnimation=new ScaleAnimation(0,1,0,1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(ANIMATION_DURATION);

        //透明动画
        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1);
        alphaAnimation.setDuration(ANIMATION_DURATION);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        igSplash.startAnimation(animationSet);

    }

    @Override
    protected void initTitle() {

        L_.e("StartActivity  initTitle");
        SystemBarHelper.tintStatusBar(this, UIUtils.getColor(R.color.colorBlack));
        playAnimation();
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent _intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(_intent);
                finish();
            }
        },3*1000);
    }

    @Override
    protected int loadViewLayout() {
        return R.layout.start_main;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
