package com.lsjr.zizisteward.activity.login.logfragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyphenate.DemoHelper;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.db.DemoDBManager;
import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.activity.home.ui.HomeActivity;
import com.lsjr.zizisteward.activity.login.presenter.LoginPresenter;
import com.lsjr.zizisteward.activity.login.view.ILoginView;
import com.lsjr.zizisteward.bean.LoginInfo;
import com.lsjr.zizisteward.coustom.CustomVideoView;
import com.lsjr.zizisteward.utils.CustomDialogUtils;
import com.yangshao.utils.L_;
import com.yangshao.utils.T_;
import com.ys.lib.base.BaseMvpFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import static com.lsjr.zizisteward.R.id.tv_cellphone;
import static com.lsjr.zizisteward.R.id.tv_password;


public class AccountLoginFragment extends BaseMvpFragment<LoginPresenter> implements View.OnClickListener,ILoginView{

    @BindView(tv_cellphone)
    EditText tvCellphone;
    @BindView(tv_password)
    EditText tvPassword;
    @BindView(R.id.iv_mima_miwen)
    ImageView ivMimaMiwen;
    @BindView(R.id.iv_mima_mingwen)
    ImageView ivMimaMingwen;
    @BindView(R.id.re_mima)
    RelativeLayout reMima;

    @BindView(R.id.tv_forget_psd)
    TextView tvForgetPsd;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.welcome_videoview)
    CustomVideoView customVideoView;
    String phone;
    String password;


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ivMimaMiwen.setVisibility(View.VISIBLE);
        ivMimaMingwen.setVisibility(View.GONE);
        tvPassword.setInputType(0x81);
        tvPassword.setSelection(tvPassword.length());
        tvLogin.setOnClickListener(this);
        reMima.setOnClickListener(this);
        customVideoView.setVideoURI(Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.kr36));
        customVideoView.start();
        customVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                customVideoView.start();

            }
        });
    }

    @Override
    protected void initTitle() {

    }


    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup viewGrop) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_account_login, null);
    }

    @Override
    protected void bindView(View view) {

    }


    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.re_mima:
                if (ivMimaMiwen.getVisibility() == View.VISIBLE && ivMimaMingwen.getVisibility() == View.GONE) {
                    ivMimaMiwen.setVisibility(View.GONE);
                    ivMimaMingwen.setVisibility(View.VISIBLE);
                    tvPassword.setInputType(0x90);
                    tvPassword.setSelection(tvPassword.length());

                } else if (ivMimaMiwen.getVisibility() == View.GONE
                        && ivMimaMingwen.getVisibility() == View.VISIBLE) {
                    ivMimaMiwen.setVisibility(View.VISIBLE);
                    ivMimaMingwen.setVisibility(View.GONE);
                    tvPassword.setInputType(0x81);
                    tvPassword.setSelection(tvPassword.length());
                }

                break;
            case R.id.tv_forget_psd:

                break;

            case R.id.tv_login:
                phone = tvCellphone.getText().toString().trim();
                password = tvPassword.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    T_.showToastReal( "请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    T_.showToastReal( "密码不能为空");
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("name", phone);
                map.put("pwd", password);
                map.put("OPT", "1");
                map.put("device", "17607168851");
                CustomDialogUtils.startCustomProgressDialog(getActivity(), "正在登录!");
                createPresenter().getLogin(map);
                break;
        }
    }
    private static final int sleepTime = 2000;
    @Override
    public void onloginSuccess(final String response) {
        L_.e("response"+response);
        CustomDialogUtils.stopCustomProgressDialog(getActivity());
//        if (EMClient.getInstance().isLoggedInBefore()){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    // ** 免登陆情况 加载所有本地群和会话
//                    //不是必须的，不加sdk也会自动异步去加载(不会重复加载)；
//                    //加上的话保证进了主页面会话和群组都已经load完毕
//                    long start = System.currentTimeMillis();
//                    EMClient.getInstance().groupManager().loadAllGroups();
//                    EMClient.getInstance().chatManager().loadAllConversations();
//                    long costTime = System.currentTimeMillis() - start;
//                    //等待sleeptime时长
//                    if (sleepTime - costTime > 0) {
//                        try {
//                            Thread.sleep(sleepTime - costTime);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });
//        }else {
//            createPresenter().EaseLogin(phone,password,response);
//        }
        openActivity(HomeActivity.class);


       // openActivity(ClassifyActivity.class);

    }

    @Override
    public void onloginError() {
        CustomDialogUtils.stopCustomProgressDialog(getActivity());
        T_.showToastReal("请求错误");
    }
}
