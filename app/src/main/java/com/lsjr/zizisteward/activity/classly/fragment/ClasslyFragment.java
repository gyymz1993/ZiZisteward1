package com.lsjr.zizisteward.activity.classly.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.activity.classly.adapter.GridViewAdapter;
import com.lsjr.zizisteward.activity.classly.adapter.SpacesItemDecoration;
import com.lsjr.zizisteward.activity.classly.presenter.ClasslyPresenter;
import com.lsjr.zizisteward.activity.classly.view.IClasslyView;
import com.lsjr.zizisteward.bean.Commodity;
import com.yangshao.utils.T_;
import com.ys.lib.anythingpull.AnythingPullLayout;
import com.ys.lib.base.BaseMvpFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;


public class ClasslyFragment extends BaseMvpFragment implements IClasslyView,AnythingPullLayout.OnPullListener{
    @BindView(R.id.id_recyview)
    RecyclerView idRecyview;
    @BindView(R.id.pull_layout)
    AnythingPullLayout pullLayout;
    GridViewAdapter gridViewAdapter;
    private int cuPager=1;
    private static final int ON_REFRESH=1;
    private static final int ON_LOAD=2;
    private int pullStatus;
    List<Commodity.FamousShopBean> famousShop;

    @Override
    protected ClasslyPresenter createPresenter() {
        return new ClasslyPresenter(this);
    }

    public void loadNetData(){
        HashMap<String, String> map = new HashMap<>();
        map.put("OPT", "425");
        map.put("currPage", String.valueOf(cuPager));
        createPresenter().getClasslyData(map);
    }
    @Override
    protected void initData() {
       // loadNetData();
    }

    @Override
    protected void initView() {
        pullLayout.autoRefresh();
        famousShop=new ArrayList<>();
        gridViewAdapter=new GridViewAdapter(getActivity());
        GridLayoutManager mgr=new GridLayoutManager(getActivity(),2);
        //设置item间距，5dp
        idRecyview.addItemDecoration(new SpacesItemDecoration(8));
        idRecyview.setLayoutManager(mgr);
        idRecyview.setAdapter(gridViewAdapter);
        pullLayout.setOnPullListener(this);

    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
       // pullLayout.autoRefresh();
        //loadNetData();
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup viewGrop) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_new, null);

    }

    @Override
    protected void bindView(View view) {
       // SystemBarHelper.immersiveStatusBar(getActivity());
    }


    @Override
    public void onLoadDataSucceed(String result) {
        T_.showToastReal("请求成功");
        famousShop.clear();
        Commodity commodity = new  Gson().fromJson(result, Commodity.class);
        famousShop = commodity.getFamousShop();
        endNetRequse();
    }


    @Override
    public void onRefreshStart(final AnythingPullLayout pullLayout) {
        cuPager=1;
        pullStatus=ON_REFRESH;
        loadNetData();
    }

    @Override
    public void onLoadStart(final AnythingPullLayout pullLayout) {
        cuPager++;
        pullStatus=ON_LOAD;
        loadNetData();
    }


    public void endNetRequse() {

        if (pullStatus==ON_LOAD){
            gridViewAdapter.addmFamousShop(famousShop);
            pullLayout.responseload(true);
//            loadAble=new Runnable() {
//                @Override
//                public void run() {
//                    pullLayout.responseload(true);
//                }
//            };
//            pullLayout.post(loadAble);
        }
        if (pullStatus==ON_REFRESH){
            gridViewAdapter.setmFamousShop(famousShop);
            pullLayout.responseRefresh(true);
//            refreshAble=new Runnable() {
//                @Override
//                public void run() {
//                    pullLayout.responseRefresh(true);
//                }
//            };
//            pullLayout.post(refreshAble);
        }
        pullStatus=0;
    }

}
