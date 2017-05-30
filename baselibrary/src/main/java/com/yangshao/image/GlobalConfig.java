package com.yangshao.image;

import android.content.Context;

/**
 * Created by doudou on 2017/4/10.
 */

public class GlobalConfig {
    private static IEngineIgLoader loader;
    public static IEngineIgLoader getLoader(Context context) {
        if (loader == null) {
            loader = new GlideLoader(context);
        }
        return loader;
    }

    public static IEngineIgLoader getLoader() {
        if (loader != null) {
            return loader;
        }else {
            new Throwable("请初始化");
        }
        return null;
    }
}
