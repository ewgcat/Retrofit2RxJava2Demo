package com.lishuaihua.retrofit.net.download

import com.lishuaihua.retrofit.net.download.DownLoadManager

abstract class CallBack {
    fun onStart() {}
    fun onCompleted() {}
    abstract fun onError(e: Throwable?)
    fun onProgress(fileSizeDownloaded: Long) {}
    abstract fun onSucess(path: String?, name: String?, fileSize: Long)
}