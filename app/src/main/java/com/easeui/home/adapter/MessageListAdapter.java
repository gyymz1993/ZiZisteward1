package com.easeui.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.chat.EMConversation;
import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.coustom.RoundImageView;
import com.yangshao.utils.L_;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 创建人：gyymz1993
 * 创建时间：2017/5/28/14:07
 **/
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    private List<EMConversation> mConversationList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public MessageListAdapter(Context context, List<EMConversation> conversationList) {
        L_.e("MessageListAdapter");
        mConversationList = conversationList;
        mContext = context.getApplicationContext();
        mLayoutInflater = LayoutInflater.from(mContext);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.hy_item_msg, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // 用户聊天内容类型
        String msgType = mConversationList.get(position).getType().toString();
        // 用户帐号
        String conversationId = mConversationList.get(position).conversationId();
        // 用户聊天内容
        String msg = mConversationList.get(position).getLastMessage().toString();

        // 用户聊天的时间
        String time = returnTime(mConversationList.get(position)
            .getLastMessage().getMsgTime());
        // 消息未读数
        int unreadMsgCount = mConversationList.get(position).getUnreadMsgCount();
        // 所有消息数
        int allMsgCount = mConversationList.get(position).getAllMsgCount();
        if (position == 0) {
            viewHolder.icon.setImageResource(R.drawable.icon_head);
            viewHolder.userName.setText("(孜孜管家)");
        } else {
            viewHolder.icon.setImageResource(R.drawable.icon_head);
            viewHolder.userName.setText(conversationId == null ? "" : conversationId);
        }
        viewHolder.content.setText(msg == null ? "聊天内容" : msg);
        viewHolder.time.setText(time == null ? "时间" : time);
        viewHolder.icon.setImageResource(R.drawable.icon_head);
       // viewHolder.content.setText(mStringList.get(position));
        //viewHolder.time.setText(mStringList.get(position));
        // List<EMMessage> allMessages = mConversationList.get(i).getAllMessages();
        // 昵称
//        lcBean.setNike(EaseUserUtils.ReturnNike(mConversationList.get(i)
//            .getUserName()));

        // 头像
//        lcBean.setPhoto(EaseUserUtils.ReturnAvatar(mConversationList.get(i)
//            .getUserName()));
    }

    @Override
    public int getItemCount() {
        return mConversationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RoundImageView icon;
        TextView userName;
        TextView content;
        TextView time;

        public ViewHolder(View view) {
            super(view);
            icon = (RoundImageView) view.findViewById(R.id.iv_icon);
            userName = (TextView) view.findViewById(R.id.tv_name);
            content = (TextView) view.findViewById(R.id.tv_content);
            time = (TextView) view.findViewById(R.id.tv_time);
        }
    }

    public static String returnTime(long space) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        SimpleDateFormat format_year = new SimpleDateFormat("yyyy");
        SimpleDateFormat format_month = new SimpleDateFormat("MM");
        SimpleDateFormat format_day = new SimpleDateFormat("dd");
        SimpleDateFormat format_time = new SimpleDateFormat("HH:mm");

        //系统时间
        Date current_date = new Date(System.currentTimeMillis());
        int current_year = Integer.valueOf(format_year.format(current_date));
        int current_month = Integer.valueOf(format_month.format(current_date));
        int current_day = Integer.valueOf(format_day.format(current_date));
        String current_time = format_time.format(current_date);

        //消息时间
        Date actual_date = new Date(space);
        int actual_year = Integer.valueOf(format_year.format(actual_date));
        int actual_month = Integer.valueOf(format_month.format(actual_date));
        int actual_day = Integer.valueOf(format_day.format(actual_date));
        String actual_time = format_time.format(actual_date);

        if (current_year > actual_year) {
            return "三天前";
        } else if (current_year == actual_year) {
            if (current_month > actual_month) {
                return "三天前";
            } else if (current_month == actual_month) {
                int _space = current_day - actual_day;
                if (_space == 1) {
                    return "昨天";
                } else if (_space == 2) {
                    return "前天";
                } else if (_space == 0) {
                    return actual_time;
                } else {
                    return "三天前";
                }
            } else {
                return "三天前";
            }
        } else {
            return "三天前";
        }
    }
}
