package com.easeui.home.fragment;

import android.support.v4.app.Fragment;


import java.util.ArrayList;


/**
 * 主界面Fragment控制器
 */
public class FragmentController {


    private ArrayList<Fragment> fragments;
    private static FragmentController controller;
    private AddressListFragment mAddressListFragment;
    private MessageFragment mMessageFragment;
    private TopicFragment mTopicFragment;
    private FindFragment mFindFragment;

    public static FragmentController getInstance() {
        if (controller == null) {
            controller = new FragmentController();
        }
        return controller;
    }

    public static void onDestroy() {
        controller = null;
    }

    private FragmentController() {
        initFragment();
    }

    private void initFragment() {
        if (fragments == null) {
            fragments = new ArrayList<>();
        }
        if (mAddressListFragment == null) {
            mAddressListFragment = new AddressListFragment();
        }
        if (mMessageFragment == null) {
            mMessageFragment = new MessageFragment();
        }
        if (mTopicFragment == null) {
            mTopicFragment = new TopicFragment();
        }
        if (mFindFragment == null) {
            mFindFragment = new FindFragment();
        }
        fragments.add(mMessageFragment);
        fragments.add(mTopicFragment);
        fragments.add(mFindFragment);
        fragments.add(mAddressListFragment);

    }


    public Fragment getFragment(int position) {
        return fragments.get(position);
    }
}