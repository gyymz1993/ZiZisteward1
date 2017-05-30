package com.lsjr.zizisteward;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v4.BuildConfig;

//import com.easemob.redpacketsdk.RPInitRedPacketCallback;
//import com.easemob.redpacketsdk.RPValueCallback;
//import com.easemob.redpacketsdk.RedPacket;
//import com.easemob.redpacketsdk.bean.RedPacketInfo;
//import com.easemob.redpacketsdk.bean.TokenData;
//import com.easemob.redpacketsdk.constant.RPConstant;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.hyphenate.DemoApplication;
import com.hyphenate.DemoHelper;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.yangshao.base.BaseApplication;
import com.yangshao.image.ImageLoader;
import com.yangshao.loading.BaseLoadingLayout;

import org.xutils.x;

import cn.sharesdk.framework.ShareSDK;

/**
 * 创建人：gyymz1993
 * 创建时间：2017/4/14/12:13
 **/
public class MyApplication extends Application {


    // 自己微信应用的 appId
    public static String WX_APP_ID = "wx388b90f68846eb73";
    // 自己微信应用的 appSecret
    public static String WX_SECRET = "ab78f012fd5b2ce002cd1b3fe209265b";
    public static String WX_CODE = "";

    //商户号
    public static final String WX_MCH_ID = "1317019401";
    //  API密钥，在商户平台设置
    public static final String API_KEY = "34e5ae602475c96bf826cf425b9b77c5";


    // 自己qq应用的 appId
    public static String QQ_APP_ID = "1105677331";
    public static String QQ_APP_KEY = "v4RibiP3xgbECbX7";


    public static IWXAPI wxApi;
    public static Tencent mTencent;

    @Override
    public void onCreate() {
        super.onCreate();
        BaseApplication.instance().initialize(this);
        DemoApplication.getInstance().initialize(this);

        //ImageLoader.initLoaderFactory(this);
        Fresco.initialize(this);
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.


        LoadingLayoutInit();

        wxApi = WXAPIFactory.createWXAPI(this, WX_APP_ID, true);
        wxApi.registerApp(WX_APP_ID);

        mTencent = Tencent.createInstance(QQ_APP_ID, this.getApplicationContext());

        /*微博*/
        ShareSDK.initSDK(this);
        //MultiDex.install(this) ;
      //  initHuanxin();
    }

    public void initHuanxin() {

//        //init demo helper
//        DemoHelper.getInstance().init(this);
//        EMOptions options = new EMOptions();
//        // 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
//        //如果你的 APP 中有第三方的服务启动，请在初始化 SDK
//        EMClient.getInstance().init(this, options);
//        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);
        MultiDex.install(this);
        //init demo helper
        DemoHelper.getInstance().init(this);

        //red packet code : 初始化红包SDK，开启日志输出开关
//        RedPacket.getInstance().initRedPacket(this, RPConstant.AUTH_METHOD_EASEMOB, new RPInitRedPacketCallback() {
//
//            @Override
//            public void initTokenData(RPValueCallback<TokenData> callback) {
//                TokenData tokenData = new TokenData();
//                tokenData.imUserId = EMClient.getInstance().getCurrentUser();
//                //此处使用环信id代替了appUserId 开发者可传入App的appUserId
//                tokenData.appUserId = EMClient.getInstance().getCurrentUser();
//                tokenData.imToken = EMClient.getInstance().getAccessToken();
//                //同步或异步获取TokenData 获取成功后回调onSuccess()方法
//                callback.onSuccess(tokenData);
//            }
//
//            @Override
//            public RedPacketInfo initCurrentUserSync() {
//                //这里需要同步设置当前用户id、昵称和头像url
//                String fromAvatarUrl = "";
//                String fromNickname = EMClient.getInstance().getCurrentUser();
//                EaseUser easeUser = EaseUserUtils.getUserInfo(fromNickname);
//                if (easeUser != null) {
//                    fromAvatarUrl = TextUtils.isEmpty(easeUser.getAvatar()) ? "none" : easeUser.getAvatar();
//                    fromNickname = TextUtils.isEmpty(easeUser.getNick()) ? easeUser.getUsername() : easeUser.getNick();
//                }
//                RedPacketInfo redPacketInfo = new RedPacketInfo();
//                redPacketInfo.fromUserId = EMClient.getInstance().getCurrentUser();
//                redPacketInfo.fromAvatarUrl = fromAvatarUrl;
//                redPacketInfo.fromNickName = fromNickname;
//                return redPacketInfo;
//            }
//        });
//        RedPacket.getInstance().setDebugMode(true);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        DemoApplication.getInstance().attachBaseContext();
    }


    private void LoadingLayoutInit() {
        BaseLoadingLayout.getmLoadConfiger()
                .setErrorText("出错啦~请稍后重试！")
                .setEmptyText("抱歉，暂无数据")
                .setNoNetworkText("无网络连接，请检查您的网络···")
                .setErrorImage(R.mipmap.ic_launcher_round)
                .setEmptyImage(R.mipmap.ic_launcher_round)
                .setNoNetworkImage(R.mipmap.ic_launcher_round)
                .setAllTipTextColor(R.color.black)
                .setAllTipTextSize(14)
                .setReloadButtonText("点我重试哦")
                .setReloadButtonTextSize(14)
                .setReloadButtonTextColor(R.color.black)
                .setReloadButtonWidthAndHeight(150, 40);
    }


}
