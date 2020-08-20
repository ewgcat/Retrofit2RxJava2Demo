package com.example.myapplication;

import android.app.Application;

import com.lishuaihua.retrofit.net.client.RetrofitClient;

import okhttp3.OkHttpClient;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        RetrofitClient.init(getApplicationContext(), "http://wechat.kai-men.cn/pmsSrv/api/api!gateway.action/",R.raw.fullchain,builder,BuildConfig.DEBUG);
    }
}
