package com.lsjr.zizisteward.activity.home.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.easeui.activity.message.ui.MessageActivity;
import com.google.gson.Gson;
import com.hyphenate.ui.LoginActivity;
import com.lsjr.zizisteward.Config;
import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.activity.call.ui.CallActivity;
import com.lsjr.zizisteward.activity.group.fragment.GroupFragment;
import com.lsjr.zizisteward.activity.home.fragment.HomeFragment;
import com.lsjr.zizisteward.activity.home.presenter.HomePresenter;
import com.lsjr.zizisteward.activity.home.view.IHomeView;
import com.lsjr.zizisteward.activity.me.MeFragment;
import com.lsjr.zizisteward.activity.scope.ui.ScopeFragment;
import com.lsjr.zizisteward.bean.AddressBookBean;
import com.lsjr.zizisteward.coustom.NoScrollViewPager;
import com.yangshao.bottomtab.BottomTabView;
import com.yangshao.title.NavigationBarView;
import com.yangshao.utils.L_;
import com.ys.lib.base.BaseMvpActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


public class HomeActivity extends BaseMvpActivity<HomePresenter> implements IHomeView {

    FragmentPagerAdapter adapter;
    public int currentPosition = -1;
    @BindView(R.id.id_nativgation_view)
    public NavigationBarView naView;
    @BindView(R.id.viewPager)
    public NoScrollViewPager viewPager;
    @BindView(R.id.bottomTabView)
    public BottomTabView bottomTabView;
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<BottomTabView.TabItemView> tabItemViews = new ArrayList<>();
    private String[] titles = new String[]{"首页", "视界", "圈子", "个人"};

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // EventBus.getDefault().register(this);
        initParams();
    }

    public void initParams() {
        viewPager.setOffscreenPageLimit(4);//设置ViewPager的缓存界面数,默认缓存为2
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return getFragments().get(position);
            }

            @Override
            public int getCount() {
                return getFragments().size();
            }
        };
        viewPager.setAdapter(adapter);
        if (getCenterView() == null) {
            bottomTabView.setTabItemViews(getTabViews());
        } else {
            bottomTabView.setTabItemViews(getTabViews(), getCenterView());
        }

        if (getOnTabItemSelectListener() != null) {
            bottomTabView.setOnTabItemSelectListener(getOnTabItemSelectListener());
        }
        bottomTabView.setOnSecondSelectListener(new BottomTabView.OnSecondSelectListener() {
            @Override
            public void onSecondSelect(int position) {

            }
        });
        if (getOnPageChangeListener() != null) {
            viewPager.addOnPageChangeListener(getOnPageChangeListener());
        }

    }


    public void requestNet(){
        L_.e("requestNet");
        Map<String, String> map = new HashMap<>();
        map.put("OPT", "206");
        map.put("user_id", Config.USER_ID);
        createPresenter().easeLogin(map);
    }



    protected List<BottomTabView.TabItemView> getTabViews() {
        tabItemViews.add(new BottomTabView.TabItemView(this, "首页", R.color.tab_normal, R.color.tab_selected, R.drawable.home_page_off, R.drawable.home_page_on));
        tabItemViews.add(new BottomTabView.TabItemView(this, "视界", R.color.tab_normal, R.color.tab_selected, R.drawable.data_off, R.drawable.data_on));
        tabItemViews.add(new BottomTabView.TabItemView(this, "圈子", R.color.tab_normal, R.color.tab_selected, R.drawable.recommend_off, R.drawable.recommend_on));
        tabItemViews.add(new BottomTabView.TabItemView(this, "个人", R.color.tab_normal, R.color.tab_selected, R.drawable.tribe_off, R.drawable.tribe_on));
        return tabItemViews;
    }

    Fragment homeFrg, scopeFrg, groupFrg, meFrg;

    protected List<Fragment> getFragments() {
        fragments = new ArrayList<>();
        fragments.add(homeFrg);
        fragments.add(scopeFrg);
        fragments.add(groupFrg);
        fragments.add(meFrg);
        return fragments;
    }

    protected ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (getFragments().get(position) instanceof GroupFragment) {
                   requestNet();
                } else {
                    currentPosition = position;
                    bottomTabView.updatePosition(position);
                    naView.setTitleText(titles[position]);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }

    protected BottomTabView.OnTabItemSelectListener getOnTabItemSelectListener() {
        return new BottomTabView.OnTabItemSelectListener() {
            @Override
            public void onTabItemSelect(int position) {
                if (getFragments().get(position) instanceof GroupFragment) {
                   requestNet();
                    //openActivity(LoginActivity.class);
                } else {
                    if (getFragments().get(position)instanceof HomeFragment||
                            getFragments().get(position)instanceof ScopeFragment){
                        naView.setVisibility(View.GONE);
                    }else {
                        naView.setVisibility(View.VISIBLE);
                        naView.getLeftimageView().setVisibility(View.VISIBLE);
                    }
                    currentPosition = position;
                    naView.setTitleText(titles[position]);
                    viewPager.setCurrentItem(position, true);

                }
            }
        };
    }

    protected String getInitTitle() {
        return titles[0];
    }

    protected View getCenterView() {
        ImageView centerView = new ImageView(this);
        centerView.setImageResource(R.drawable.main_call);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 200);
        layoutParams.leftMargin = 60;
        layoutParams.rightMargin = 60;
        layoutParams.bottomMargin = 40;
        centerView.setLayoutParams(layoutParams);
        centerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(CallActivity.class);
            }
        });
        return centerView;
    }


    @Override
    public void initView() {
        homeFrg = new HomeFragment();
        scopeFrg = new ScopeFragment();
        groupFrg = new GroupFragment();
        meFrg = new MeFragment();
    }

    @Override
    protected void initTitle() {
        if (naView != null) {
            naView.setTitleText(titles[0]);
            naView.getLeftimageView().setVisibility(View.GONE);
            naView.setVisibility(View.GONE);
        }
    }

    @Override
    protected int loadViewLayout() {
        return R.layout.activity_home;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (bottomTabView != null && currentPosition != -1) {
            bottomTabView.updatePosition(currentPosition);
            viewPager.setCurrentItem(currentPosition);
        }
    }


    @Override
    public void easeLoignSucceed(String result) {
        /* 得到朋友列表*/


       // openActivity(Login.class);

        AddressBookBean abBean = new Gson().fromJson(result, AddressBookBean.class);
        //EventBus.getDefault().post(abBean);
        openActivity(com.easeui.home.ui.HomeActivity.class);
//        L_.e(abBean.toString());
//        Bundle bundle=new Bundle();
//        bundle.putSerializable(Config.SP_ADRESS_BOOK_KEY,abBean);
//        openActivity(GroupActivity.class,bundle);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }


    @Override
    public void getPageDataSucceed(String result) {

    }
}
