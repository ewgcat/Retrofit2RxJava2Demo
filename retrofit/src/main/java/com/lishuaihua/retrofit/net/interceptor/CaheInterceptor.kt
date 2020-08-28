package com.lishuaihua.retrofit.net.interceptor

import android.content.Context
import android.util.Log
import com.lishuaihua.retrofit.net.utils.NetworkUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.Throws

class CaheInterceptor(private val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        return if (NetworkUtil.hasNetwork(context)) {
            val response = chain.proceed(request)
            // read from cache for 60 s
            val maxAge = 60
            val cacheControl = request.cacheControl.toString()
            Log.i("CaheInterceptor", "60s load cahe$cacheControl")
            response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .build()
        } else {
            Log.i("CaheInterceptor", " no network load cahe")
            request = request.newBuilder()
                    .cacheControl(FORCE_CACHE)
                    .build()
            val response = chain.proceed(request)
            //set cahe times is 3 days
            val maxStale = 60 * 60 * 24 * 3
            response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .build()
        }
    }
}