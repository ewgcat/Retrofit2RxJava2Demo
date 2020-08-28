package com.lishuaihua.retrofit.net.response

/**
 * 网络返回基类 支持泛型
 */
class BaseResponse<T> {
    var code = 0
    var msg: String? = null
    var data: T? = null
        private set

    fun setData(data: T) {
        this.data = data
    }

    val isOk: Boolean
        get() = code == 0
}