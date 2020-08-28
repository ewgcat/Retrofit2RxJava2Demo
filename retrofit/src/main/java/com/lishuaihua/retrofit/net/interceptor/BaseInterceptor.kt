package com.lishuaihua.retrofit.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.Throws

class BaseInterceptor(private val headers: Map<String, String>?) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request()
                .newBuilder()
        builder.addHeader("Content-Type", "application/json; charset=UTF-8").build()
        builder.addHeader("Accept-Encoding", "utf-8").build()
        builder.addHeader("Connection", "keep-alive").build()
        builder.addHeader("Accept", "*/*").build()
        builder.addHeader("Cookie", "add cookies here").build()
        if (headers != null && headers.size > 0) {
            val keys = headers.keys
            for (headerKey in keys) {
                builder.addHeader(headerKey, headers[headerKey]!!).build()
            }
        }
        return chain.proceed(builder.build())
    }
}