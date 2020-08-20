package com.example.myapplication

import com.lishuaihua.retrofit.net.client.RetrofitClient
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject



class HttpManager {

    companion object {
        private val apiService = RetrofitClient.mRetrofit.create(LoginApiService::class.java)

        /**
         * 执行请求操作
         *
         * @param observable
         * @param observer
         * @param <T>
        </T> */
        private fun <T> execute(observable: Observable<T>, observer: Observer<T>) {
            observable.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer)
        }


        /**
         * 封装必传参数
         * @param data 请求参数
         */
        private fun getBaseParams(data: JSONObject?): RequestBody {
            return RequestBody.create(MediaType.parse("application/json"), data.toString())

        }

        /**
         * 封装必传参数
         * @param data 请求参数
         */
        private fun getBaseParams(data: JSONArray?): RequestBody {
            return RequestBody.create(MediaType.parse("application/json"), data.toString())
        }



        /**
         * post 请求
         *
         * @param method
         * @param token
         * @param data
         * @param observer
         */
        open fun postLogin(param: String,  observer: Observer<JSONObject>) {
            val observable = apiService.login(param)
            execute(observable, observer)
        }






    }
}
