package com.ys.lib.base;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}
