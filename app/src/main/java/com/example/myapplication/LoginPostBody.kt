package com.example.myapplication

import android.util.Base64
import org.json.JSONException
import org.json.JSONObject

class LoginPostBody(username: String?, password: String, clientId: String?) {
    private val mJsonParmas = JSONObject()
    private val mContentParam = JSONObject()
    fun getmContentParam(): JSONObject {
        return mContentParam
    }

    init {
        try {
            mJsonParmas.put("username", username)
            mJsonParmas.put("password", Base64.encodeToString(password.toByteArray(), Base64.DEFAULT))
            mJsonParmas.put("platform", 1)
            mJsonParmas.put("clientId", clientId)
            mContentParam.put("tranCode", 1017)
            mContentParam.put("isEncryption", 0)
            mContentParam.put("bizContent", mJsonParmas)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}