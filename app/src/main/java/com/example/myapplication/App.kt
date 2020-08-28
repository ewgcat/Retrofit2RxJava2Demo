package com.example.myapplication

import android.app.Application
import com.lishuaihua.retrofit.net.client.RetrofitClient
import okhttp3.OkHttpClient

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val builder = OkHttpClient.Builder()
        RetrofitClient.init(applicationContext, "http://wechat.kai-men.cn/pmsSrv/api/api!gateway.action/", R.raw.fullchain, builder, BuildConfig.DEBUG)
    }
}