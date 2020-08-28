package com.lishuaihua.retrofit.net.response

interface ResultCallBack<T> {
    fun onSuccess(result: T)
    fun onFail(msg: String?, code: Int)
}