package com.yangshao.image;

import android.content.Context;

/**
  * @author: gyymz1993
  * 创建时间：2017/4/1 17:50
  * @version  imagerLoader生成器
  *
 **/
public class ImageLoader {

    private static Context mContext;
    /**
     * 加载普通图片
     *
     * @param context
     * @return
     */
    public static SingleConfig.ConfigBuilder with(Context context) {
        return new SingleConfig.ConfigBuilder(context.getApplicationContext());
    }

    /**
     * 加载普通图片
     * @return
     */
    public static SingleConfig.ConfigBuilder with() {
        return new SingleConfig.ConfigBuilder(mContext);
    }

    /**
     * @param context        上下文
     */
    public static void init(final Context context) {
        ImageLoader.mContext = context.getApplicationContext();
        GlobalConfig.getLoader(mContext);
    }

}
