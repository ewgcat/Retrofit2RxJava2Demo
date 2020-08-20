package com.lishuaihua.retrofit.net.response;


import android.text.TextUtils;


import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class BaseObserver<JSONObject> implements Observer<JSONObject>, ResultCallBack<JSONObject>, LifecycleObserver {
    private Disposable disposable;
    private Lifecycle lifecycle;


    public BaseObserver(Lifecycle lifecycle) {

        this.lifecycle = lifecycle;
        if (null != lifecycle) {
            this.lifecycle.addObserver(this);
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(LifecycleOwner owner) {
        destroy();
    }

    private void destroy() {
        if (lifecycle != null) {
            lifecycle.removeObserver(this);
        }
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }


    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onComplete() {
        destroy();
    }

    @Override
    public void onNext(JSONObject jsonObject) {
        onSuccess(jsonObject );
    }


    @Override
    public void onError(Throwable e) {
        onFail(getErrorMsg(e),500);
        onComplete();
    }

    private String getErrorMsg(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            return "连接超时";
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
            return "小主，您的手机网络不太顺畅哦~";
        } else if (e instanceof JsonSyntaxException) {
            return "解析数据出错了";
        } else {
            return "出错了";
        }
    }


}
