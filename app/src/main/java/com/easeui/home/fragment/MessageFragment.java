package com.easeui.home.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.easeui.home.adapter.MessageListAdapter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.lsjr.zizisteward.R;
import com.ys.lib.base.BaseMvpFragment;
import com.ys.lib.base.BasePresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MessageFragment extends BaseMvpFragment {


    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.ll_clear)
    LinearLayout llClear;
    @BindView(R.id.ll_background)
    LinearLayout llBackground;
    @BindView(R.id.id_rv_msglist)
    RecyclerView idRvMsglist;
    MessageListAdapter mMessageListAdapter;

    private List<EMConversation> conversationList;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup viewGrop) {
        return inflater.inflate(R.layout.hy_fragment_msg, null);
    }


    @Override
    protected void initData() {
        super.initData();
        conversationList=new ArrayList<>();
        if (conversationList.size()==0){
              /*添加一个管家账号*/
            EMConversation single = EMClient
                .getInstance()
                .chatManager()
                .getConversation("孜孜管家",
                    EaseCommonUtils.getConversationType(1), true);
            EMMessage e = EMMessage
                .createReceiveMessage(EMMessage.Type.TXT);
            EMTextMessageBody body = new EMTextMessageBody(
                "欢迎来到孜孜，小孜将竭诚为您服务!");
            e.setUnread(true);
            e.addBody(body);
            single.appendMessage(e);
            conversationList.add(0, single);
        }
        conversationList.addAll(loadConversationsWithRecentChat());
        mMessageListAdapter=new MessageListAdapter(getContext(),conversationList);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        idRvMsglist.setLayoutManager(layoutManager);
        idRvMsglist.setAdapter(mMessageListAdapter);
    }

    /**
     * load conversation list
     * 获取所有会话
     *
     * @return +
     */
    protected List<EMConversation> loadConversationsWithRecentChat() {
        // get all conversations
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        /**
         * lastMsgTime will change if there is new message during sorting
         * so use synchronized to make sure timestamp of last message won't change.
         */
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    sortList.add(new Pair<>(conversation.getLastMessage().getMsgTime(), conversation));
                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }
        return list;
    }

    /**
     * sort conversations according time stamp of last message
     * 根据最后一条消息的时间排序
     *
     * @param conversationList
     */
    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                if (con1.first.equals(con2.first)) {
                    return 0;
                } else if (con2.first.longValue() > con1.first.longValue()) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
