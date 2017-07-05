package com.example.myapplication.retrofit.login;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginPostBody {
    private JSONObject mJsonParmas = new JSONObject();
    private JSONObject mContentParam = new JSONObject();
    public  LoginPostBody(String username,String password,String clientId){
        try {
            mJsonParmas.put("username", username);
            mJsonParmas.put("password", Base64.encodeToString(password.getBytes(), Base64.DEFAULT));
            mJsonParmas.put("platform", 1);
            mJsonParmas.put("clientId",clientId);
            mContentParam.put("tranCode", 1017);
            mContentParam.put("isEncryption", 0);
            mContentParam.put("bizContent", mJsonParmas);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public JSONObject getmContentParam() {
        return mContentParam;
    }

}
