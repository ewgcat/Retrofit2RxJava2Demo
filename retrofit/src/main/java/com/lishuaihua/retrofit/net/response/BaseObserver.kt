package com.lishuaihua.retrofit.net.response

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.gson.JsonSyntaxException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseObserver<JSONObject>(private val lifecycle: Lifecycle?) : Observer<JSONObject>, ResultCallBack<JSONObject>, LifecycleObserver {
    private var disposable: Disposable? = null
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner?) {
        destroy()
    }

    private fun destroy() {
        lifecycle?.removeObserver(this)
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
        }
    }

    override fun onSubscribe(d: Disposable) {
        disposable = d
    }

    override fun onComplete() {
        destroy()
    }

    override fun onNext(jsonObject: JSONObject) {
        onSuccess(jsonObject)
    }

    override fun onError(e: Throwable) {
        onFail(getErrorMsg(e), 500)
        onComplete()
    }

    private fun getErrorMsg(e: Throwable): String {
        return if (e is SocketTimeoutException) {
            "连接超时"
        } else if (e is ConnectException || e is UnknownHostException) {
            "小主，您的手机网络不太顺畅哦~"
        } else if (e is JsonSyntaxException) {
            "解析数据出错了"
        } else {
            "出错了"
        }
    }

    init {
        if (null != lifecycle) {
            lifecycle.addObserver(this)
        }
    }
}