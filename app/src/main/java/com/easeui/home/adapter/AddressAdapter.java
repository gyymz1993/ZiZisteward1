package com.easeui.home.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.bean.AddressBookBean;
import com.lsjr.zizisteward.http.AppUrl;
import com.yangshao.image.ImageLoader;
import com.yangshao.image.utils.ScaleMode;

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
            //viewHolder.icon.setImageResource(Integer.valueOf(friendsBean.getPhoto()));
            ImageLoader.with()
                .res(Integer.valueOf(friendsBean.getPhoto()))
                .placeHolder(R.mipmap.ic_launcher)
                .scale(ScaleMode.FIT_CENTER)
                .into(viewHolder.icon);
        }else {
//            Glide.with(mContext).load(AppUrl.Http +friendsBean.getPhoto())
//                .into(viewHolder.icon);
//            ImageLoader.with()
//                .url(AppUrl.Http +friendsBean.getPhoto())
//                .placeHolder(R.mipmap.ic_launcher)
//                .scale(ScaleMode.FIT_CENTER)
//                .into(viewHolder.icon);
            ViewPropertyAnimation.Animator animationObject = new ViewPropertyAnimation.Animator() {
                @Override
                public void animate(View view) {
                    view.setAlpha( 0f );

                    ObjectAnimator fadeAnim = ObjectAnimator.ofFloat( view, "alpha", 0f, 1f );
                    fadeAnim.setDuration( 2500 );
                    fadeAnim.start();
                }
            };
            ImageLoader.with()
                .url(AppUrl.Http +friendsBean.getPhoto())
                .placeHolder(R.mipmap.ic_launcher)
                .scale(ScaleMode.FIT_CENTER)
                .animate(animationObject)
                .asCircle()
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
