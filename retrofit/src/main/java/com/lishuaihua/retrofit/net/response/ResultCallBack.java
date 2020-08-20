package com.lishuaihua.retrofit.net.response;


public interface ResultCallBack<T> {
    void onSuccess(T result);

    void onFail(String msg, int code);
}
