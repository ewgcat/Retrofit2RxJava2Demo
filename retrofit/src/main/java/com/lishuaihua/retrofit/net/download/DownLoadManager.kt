package com.lishuaihua.retrofit.net.download

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import okhttp3.ResponseBody
import java.io.*

class DownLoadManager(callBack: CallBack?) {
    private val callBack: CallBack?
    private var handler: Handler? = null
    fun writeResponseBodyToDisk(context: Context, body: ResponseBody): Boolean {
        Log.d(TAG, "contentType:>>>>" + body.contentType().toString())
        val type = body.contentType().toString()
        if (type == APK_CONTENTTYPE) {
            fileSuffix = ".apk"
        } else if (type == PNG_CONTENTTYPE) {
            fileSuffix = ".png"
        } else if (type == JPG_CONTENTTYPE) {
            fileSuffix = ".jpg"
        }

        // 其他同上 自己判断加入
        val name = System.currentTimeMillis().toString() + fileSuffix
        val path = context.getExternalFilesDir(null).toString() + File.separator + name
        Log.d(TAG, "path:>>>>$path")
        return try {
            // todo change the file location/name according to your needs
            val futureStudioIconFile = File(path)
            if (futureStudioIconFile.exists()) {
                futureStudioIconFile.delete()
            }
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                val fileReader = ByteArray(4096)
                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0
                Log.d(TAG, "file length: $fileSize")
                inputStream = body.byteStream()
                outputStream = FileOutputStream(futureStudioIconFile)
                while (true) {
                    val read = inputStream.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
                    Log.d(TAG, "file download: $fileSizeDownloaded of $fileSize")
                    if (callBack != null) {
                        handler = Handler(Looper.getMainLooper())
                        val finalFileSizeDownloaded = fileSizeDownloaded
                        handler!!.post { callBack.onProgress(finalFileSizeDownloaded) }
                    }
                }
                outputStream.flush()
                Log.d(TAG, "file downloaded: $fileSizeDownloaded of $fileSize")
                if (callBack != null) {
                    handler = Handler(Looper.getMainLooper())
                    handler!!.post { callBack.onSucess(path, name, fileSize) }
                    Log.d(TAG, "file downloaded: $fileSizeDownloaded of $fileSize")
                }
                true
            } catch (e: IOException) {
                if (callBack != null) {
                    callBack.onError(e)
                }
                false
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            if (callBack != null) {
                callBack.onError(e)
            }
            false
        }
    }

    companion object {
        private const val TAG = "DownLoadManager"
        private const val APK_CONTENTTYPE = "application/vnd.android.package-archive"
        private const val PNG_CONTENTTYPE = "image/png"
        private const val JPG_CONTENTTYPE = "image/jpg"
        private var fileSuffix = ""
        private var sInstance: DownLoadManager? = null

        /**
         * DownLoadManager getInstance
         */
        @Synchronized
        fun getInstance(callBack: CallBack?): DownLoadManager? {
            if (sInstance == null) {
                sInstance = DownLoadManager(callBack)
            }
            return sInstance
        }
    }

    init {
        this.callBack = callBack
    }
}