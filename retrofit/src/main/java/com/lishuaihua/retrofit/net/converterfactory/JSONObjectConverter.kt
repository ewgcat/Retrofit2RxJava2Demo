package com.lishuaihua.retrofit.net.converterfactory

import android.util.Log
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Converter
import java.io.IOException

class JSONObjectConverter : Converter<ResponseBody, JSONObject?> {
    override fun convert(responseBody: ResponseBody): JSONObject? {
        var jsonObject: JSONObject? = null
        try {
            val result = responseBody.string()
            jsonObject = JSONObject(result)
            return jsonObject
        } catch (e: JSONException) {
            Log.i("JSONObjectConverter", e.toString())
        }
        return jsonObject
    }

    companion object {
        @JvmField
        val INSTANCE = JSONObjectConverter()
    }
}