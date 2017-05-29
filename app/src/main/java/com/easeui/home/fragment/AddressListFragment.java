package com.easeui.home.fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easeui.home.adapter.AddressAdapter;
import com.easeui.home.presenter.AddressListPresenter;
import com.easeui.home.view.IAddressListView;
import com.google.gson.Gson;
import com.lsjr.zizisteward.Config;
import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.bean.AddressBookBean;
import com.yangshao.utils.UIUtils;
import com.ys.lib.base.BaseMvpFragment;
import com.ys.lib.base.BasePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
  * @author: gyymz1993
  * 创建时间：2017/5/29 13:14
  * @version   通讯录
 **/
public class AddressListFragment extends BaseMvpFragment<AddressListPresenter> implements IAddressListView {


    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.id_rv_addresslist)
    RecyclerView mRecyclerView;

    private String [] title=new String[]{"新的朋友","删除好友",
        "手机通讯录","名片夹","自己"};
    private int [] icons=new int[]{R.drawable.hy_new_friends,
    R.drawable.hy_delete_friens,R.drawable.hy_address_book,
    R.drawable.ic_launcher_round,R.drawable.ic_launcher_round};

    List<AddressBookBean.FriendsBean> defaultFriendsBeans;
    private AddressAdapter mAddressAdapter;

    @Override
    protected AddressListPresenter createPresenter() {
        return new AddressListPresenter(this);
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ( (AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
            mToolbar.setTitle("通讯录");
            mToolbar.setTitleTextColor(UIUtils.getColor(R.color.white));
            mToolbar.setNavigationIcon(R.drawable.title_back);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        defaultFriendsBeans=new ArrayList<>();
        for (int i=0;i<title.length;i++){
            AddressBookBean.FriendsBean friendsBean=new AddressBookBean.FriendsBean();
            friendsBean.setName(title[i]);
            friendsBean.setPhoto(icons[i]+"");
            defaultFriendsBeans.add(friendsBean);
        }
        mAddressAdapter=new AddressAdapter(getContext(),defaultFriendsBeans);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAddressAdapter);
        Map<String, String> map = new HashMap<>();
        map.put("OPT", "206");
        map.put("user_id", Config.USER_ID);
        createPresenter().loadAdressListforNet(map);
    }

    @Override
    public void onLoadAdressResult(String result) {
        AddressBookBean abBean = new Gson().fromJson(result, AddressBookBean.class);
        List<AddressBookBean.FriendsBean> friendsBeans = abBean.getFriends();
        defaultFriendsBeans.addAll(friendsBeans);
        mAddressAdapter.notifyDataSetChanged(defaultFriendsBeans);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup viewGrop) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_addresslist,null);
    }




}
