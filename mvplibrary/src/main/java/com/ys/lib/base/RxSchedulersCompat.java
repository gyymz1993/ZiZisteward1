package com.ys.lib.base;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/2/21.
 */
public class RxSchedulersCompat {
    public static <T> Observable.Transformer<T,T> observeOnMainThread(){
       return uiMainThread;
    }

    public static <T> Observable.Transformer<T,T> applyIoSchedulers(){
        return ioThread;
    }


    public  static final Observable.Transformer ioThread= new Observable.Transformer(){
        @Override
        public Object call(Object o) {
            return ((Observable)o).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    };

    public static final Observable.Transformer uiMainThread=new Observable.Transformer() {
        @Override
        public Object call(Object o) {
            return ((Observable)o).observeOn(AndroidSchedulers.mainThread());
        }
    };

}
