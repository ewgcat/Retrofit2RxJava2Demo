package com.example.myapplication;



import org.json.JSONObject;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;




public interface LoginApiService {

    @FormUrlEncoded
    @POST("http://wechat.kai-men.cn/pmsSrv/api/api!gateway.action")
    Observable<JSONObject> login(@Field("reqStr") String jsonStr);


}
