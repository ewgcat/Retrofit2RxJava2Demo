package com.example.myapplication

import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApiService {
    @FormUrlEncoded
    @POST("http://wechat.kai-men.cn/pmsSrv/api/api!gateway.action")
    fun login(@Field("reqStr") jsonStr: String): Observable<JSONObject>
}