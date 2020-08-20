package com.example.myapplication;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.retrofit.client.RetrofitClient;
import com.example.myapplication.retrofit.login.LoginPostBody;
import com.hengte.retrofit.net.subsrciber.BaseObserver;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        dialog = new ProgressDialog(this);
        dialog.setTitle("请稍后...");


    }

    public void login(View view){
        EditText name= (EditText) findViewById(R.id.name);
        EditText password= (EditText) findViewById(R.id.password);
        if (TextUtils.isEmpty(name.getText().toString())||TextUtils.isEmpty(password.getText().toString())){
            postLogin("zhusha5","hengte@2016");
        }else {
            postLogin(name.getText().toString(),password.getText().toString());
        }

    }
    //登陆
    private void postLogin(String name,String password) {
        dialog.show();
        LoginPostBody loginPostBody = new LoginPostBody(name, password, "11");
        RetrofitClient.getInstance(this).createLoginApi().postLogin(loginPostBody.getmContentParam(), new BaseObserver<JSONObject>(MainActivity.this) {
            @Override
            public void onFail(String error) {
                Log.i("MainActivity","onFail "+error);
                dialog.dismiss();
            }

            @Override
            public void onSuccess(JSONObject jsonObject) {
                Log.i("MainActivity","onSuccess "+jsonObject.toString());
                tv.setText("请求的返回  "+jsonObject.toString());
                dialog.dismiss();
            }
        });
    }
}
