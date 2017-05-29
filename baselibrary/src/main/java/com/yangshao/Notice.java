package com.yangshao;

/**
 * Created by Administrator on 2016/11/17 0017.
 */
public class Notice<T> {
    public int type;
    public T content;

    public Notice(int type) {
        this.type = type;
    }

    public Notice(int type, T content) {
        this.type = type;
        this.content = content;
    }

    public Notice() {
    }
}
