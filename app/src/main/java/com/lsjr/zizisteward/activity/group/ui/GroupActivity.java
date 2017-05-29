package com.lsjr.zizisteward.activity.group.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.lsjr.zizisteward.Config;
import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.activity.group.fragment.AddressListFragment;
import com.lsjr.zizisteward.activity.group.fragment.FindFragment;
import com.lsjr.zizisteward.activity.group.fragment.MessageFragment;
import com.lsjr.zizisteward.activity.group.fragment.TopicFragment;
import com.lsjr.zizisteward.bean.AddressBookBean;
import com.yangshao.base.BaseFragment;
import com.yangshao.bottomtab.BottomTabView;
import com.yangshao.title.NavigationBarView;
import com.yangshao.utils.L_;
import com.ys.lib.base.BaseMvpActivity;
import com.ys.lib.base.BasePresenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;



public class GroupActivity extends BaseMvpActivity {


    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<BottomTabView.TabItemView> tabItemViews = new ArrayList<>();
    private String[] titles=new String[]{"消息","话题","发现","通讯录"};
    FragmentPagerAdapter adapter;

    @BindView(R.id.id_nativgation_view)
    public NavigationBarView naView;
    @BindView(R.id.viewPager)
    public ViewPager viewPager;
    @BindView(R.id.bottomTabView)
    public BottomTabView bottomTabView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParams();
    }




    public  void initParams(){
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


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    protected List<BottomTabView.TabItemView> getTabViews() {
        tabItemViews.add(new BottomTabView.TabItemView(this, "消息", R.color.tab_normal, R.color.tab_selected, R.drawable.em_conversation_normal, R.drawable.em_conversation_selected));
        tabItemViews.add(new BottomTabView.TabItemView(this, "话题", R.color.tab_normal, R.color.tab_selected, R.drawable.into_ring_normal, R.drawable.icon_into_selected));
        tabItemViews.add(new BottomTabView.TabItemView(this, "发现", R.color.tab_normal, R.color.tab_selected, R.drawable.circle_friend_normal, R.drawable.circle_friend_selected));
        tabItemViews.add(new BottomTabView.TabItemView(this, "通讯录", R.color.tab_normal, R.color.tab_selected, R.drawable.em_contact_list_normal, R.drawable.em_contact_list_selected));
        return tabItemViews;
    }

    BaseFragment fragment1,fragment2,fragment3,fragment4;
    protected List<Fragment> getFragments() {
        fragments = new ArrayList<>();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        return fragments;
    }

    protected ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomTabView.updatePosition(position);
                naView.setTitleText(titles[position]);
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
                viewPager.setCurrentItem(position, true);
                naView.setTitleText(titles[position]);
            }
        };
    }

    protected String getInitTitle() {
        return titles[0];
    }


    @Override
    protected void initTitle() {
        if (naView!=null){
            naView.setTitleText(titles[0]);
            naView.getLeftimageView().setVisibility(View.GONE);
        }
    }

    @Override
    protected int loadViewLayout() {
        return R.layout.activity_group;
    }

    @Override
    public void initView() {
        fragment1=new MessageFragment();
        fragment2=new TopicFragment();
        fragment3=new FindFragment();
        fragment4=new AddressListFragment();
    }

    @Override
    protected void initData() {
       Bundle bundle  = getIntent().getExtras();
       AddressBookBean bookBean = (AddressBookBean) bundle.getSerializable(Config.SP_ADRESS_BOOK_KEY);
       L_.e(bookBean.toString());
        //createPresenter().getLogin(AppUrl.login,map);
    }




    protected View getCenterView() {
        return null;
    }

    public void onloginSuccess(String response) {
        if (mGetDataOnclickListener!=null){
            mGetDataOnclickListener.setData(response);
        }

    }

    public interface GetDataOnclickListener{
        void setData(String data);
    }

    static GetDataOnclickListener mGetDataOnclickListener;
    public static void setGetDataOnclickListener(GetDataOnclickListener getDataOnclickListener) {
        mGetDataOnclickListener = getDataOnclickListener;
    }
}
