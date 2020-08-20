package com.example.myapplication

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lishuaihua.retrofit.net.client.RetrofitClient
import com.lishuaihua.retrofit.net.response.BaseObserver
import org.json.JSONObject
import androidx.lifecycle.Lifecycle


class MainActivity : AppCompatActivity() {
    private var dialog: ProgressDialog? = null
    private var tv: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById<View>(R.id.tv) as TextView
        dialog = ProgressDialog(this)
        dialog!!.setTitle("请稍后...")
    }

    fun login(view: View?) {
        val name = findViewById<View>(R.id.name) as EditText
        val password = findViewById<View>(R.id.password) as EditText
        if (TextUtils.isEmpty(name.text.toString()) || TextUtils.isEmpty(password.text.toString())) {
            postLogin("zhusha5", "hengte@2016")
        } else {
            postLogin(name.text.toString(), password.text.toString())
        }
    }

    //登陆
    private fun postLogin(name: String, password: String) {
        dialog!!.show()
        val loginPostBody = LoginPostBody(name, password, "11")
        HttpManager.postLogin(loginPostBody.getmContentParam().toString(), object : BaseObserver<JSONObject>(lifecycle) {
            override fun onFail(error: String, code: Int) {
                Log.i("MainActivity", "onFail $error")
                dialog!!.dismiss()
            }

            override fun onSuccess(jsonObject: JSONObject) {
                Log.i("MainActivity", "onSuccess $jsonObject")
                tv!!.text = "请求的返回  $jsonObject"
                dialog!!.dismiss()
            }
        })
    }
}