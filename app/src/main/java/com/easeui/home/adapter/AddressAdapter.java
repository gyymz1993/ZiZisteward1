package com.easeui.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.bean.AddressBookBean;
import com.lsjr.zizisteward.http.AppUrl;

import java.util.List;

/**
 * 创建人：gyymz1993
 * 创建时间：2017/5/29/14:57
 **/
public class AddressAdapter  extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {


    private Context mContext;
    private LayoutInflater mInflater;
    private List<AddressBookBean.FriendsBean> mBeanList;

    public AddressAdapter(Context context, List<AddressBookBean.FriendsBean> beanList) {
        mContext = context.getApplicationContext();
        mBeanList = beanList;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       View view=mInflater.inflate(R.layout.hy_item_adresslist,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        AddressBookBean.FriendsBean friendsBean=mBeanList.get(i);
        if (i<5){
            viewHolder.icon.setImageResource(Integer.valueOf(friendsBean.getPhoto()));
        }else {
            Glide.with(mContext).load(AppUrl.Http +friendsBean.getPhoto())
                .into(viewHolder.icon);
        }
        viewHolder.name.setText(friendsBean.getName());
    }

    public void notifyDataSetChanged(List<AddressBookBean.FriendsBean> beanList){
        this.mBeanList=beanList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{
        ImageView icon;
        TextView name;

        public ViewHolder(View view) {
            super(view);
            icon=(ImageView)view.findViewById(R.id.id_ig_icon);
            name=(TextView)view.findViewById(R.id.id_tv_name);
        }
    }
}
