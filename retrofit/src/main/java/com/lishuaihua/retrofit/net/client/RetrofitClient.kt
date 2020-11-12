package com.lishuaihua.retrofit.net.client

import android.content.Context
import android.util.Log
import com.lishuaihua.retrofit.net.converterfactory.JSONObjectConverterFactory
import com.lishuaihua.retrofit.net.converterfactory.JSONObjectConverterFactory.Companion.create
import com.lishuaihua.retrofit.net.httpsfactroy.HTTPSCerUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

object RetrofitClient {

  var mRetrofit: Retrofit? = null

    //初始化一般请求客户端
    fun init(context: Context?, baseUrl: String?, rawId: Int, builder: OkHttpClient.Builder, isDebug: Boolean): Retrofit? {
        if (mRetrofit == null) {
            if (isDebug) {
                // Log信息拦截器
                val loggingInterceptor = HttpLoggingInterceptor { message -> Log.d("http", message) }
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor)
            }
            if (rawId!=0){
                HTTPSCerUtils.setCertificate(context, builder, rawId)
                builder.hostnameVerifier(HostnameVerifier { hostname, session -> true })
            }
            builder.connectTimeout(5, TimeUnit.SECONDS)
            builder.readTimeout(5, TimeUnit.SECONDS)
            builder.writeTimeout(5, TimeUnit.SECONDS)
            val okHttpClient = builder.build()

            //组装retrofit
            mRetrofit = Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(JSONObjectConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(baseUrl)
                    .build()
        }
        return mRetrofit
    }
  
}
