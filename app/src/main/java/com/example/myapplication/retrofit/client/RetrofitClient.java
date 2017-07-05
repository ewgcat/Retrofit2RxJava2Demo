package com.example.myapplication.retrofit.client;

import android.content.Context;
import android.text.TextUtils;


import com.example.myapplication.R;
import com.example.myapplication.retrofit.hostnameverifier.AllowMyHostnameVerifier;
import com.example.myapplication.retrofit.login.LoginApiService;
import com.hengte.retrofit.net.apiservice.BaseApiService;
import com.hengte.retrofit.net.converterfactory.JSONObjectConverterFactory;
import com.hengte.retrofit.net.cookie.NovateCookieManger;
import com.hengte.retrofit.net.download.CallBack;
import com.hengte.retrofit.net.httpsfactroy.HttpsFactroy;
import com.hengte.retrofit.net.interceptor.BaseInterceptor;

import com.hengte.retrofit.net.interceptor.CaheInterceptor;
import com.hengte.retrofit.net.subsrciber.DownObserver;
import com.hengte.retrofit.net.utils.FileUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;




import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class RetrofitClient {

    private static final int DEFAULT_TIMEOUT = 10;
    private BaseApiService apiService;
    private LoginApiService loginApiService;

    private static OkHttpClient okHttpClient;
    public static String baseUrl = "http://wechat.kai-men.cn/pmsSrv/api/api!gateway.action/";
    private static Context mContext;
    private static Retrofit retrofit;
    private Cache cache = null;
    private File httpCacheDirectory;
    private static int[] certificates = {R.raw.fullchain};




    private static class SingletonHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient(mContext);
    }

    public static RetrofitClient getInstance(Context context) {
        if (context != null) {
            mContext = context;
        }
        return SingletonHolder.INSTANCE;
    }

    public static RetrofitClient getInstance(Context context, String url) {
        if (context != null) {
            mContext = context;
        }

        return new RetrofitClient(context, url);
    }

    public static RetrofitClient getInstance(Context context, String url, Map headers) {
        if (context != null) {
            mContext = context;
        }
        return new RetrofitClient(context, url, headers);
    }


    private RetrofitClient(Context context) {
        this(context, baseUrl, null);
    }

    private RetrofitClient(Context context, String url) {
        this(context, url, null);
    }

    private RetrofitClient(Context context, String url, Map headers) {
        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }

        if (httpCacheDirectory == null && context != null) {
            File cacheDir = context.getCacheDir();
            if (!cacheDir.exists()) {
                try {
                    cacheDir = FileUtils.createSDDir("poly_cache");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            httpCacheDirectory = new File(cacheDir, "poly_cache");
        }

        try {
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
            }
        } catch (Exception e) {

        }


        try {

            okHttpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .cookieJar(new NovateCookieManger(context))
                    .cache(cache)
                    .addInterceptor(new BaseInterceptor(headers))
                    .addInterceptor(new CaheInterceptor(context))
                    .addNetworkInterceptor(new CaheInterceptor(context))
                    .hostnameVerifier(new AllowMyHostnameVerifier())
                    .sslSocketFactory(HttpsFactroy.getSSLSocketFactory(context, certificates), Platform.get().trustManager(HttpsFactroy.getSSLSocketFactory(context, certificates)))
                    // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                    .connectionPool(new ConnectionPool(8, 10, TimeUnit.SECONDS))
                    .connectTimeout(30000L, TimeUnit.MILLISECONDS)
                    .readTimeout(30000L, TimeUnit.MILLISECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(JSONObjectConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(url)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * create BaseApi  defalte ApiManager
     *
     * @return ApiManager
     */
    public RetrofitClient createBaseApi() {
        apiService = create(BaseApiService.class);
        return this;
    }


    public RetrofitClient createLoginApi() {
        loginApiService = create(LoginApiService.class);
        return this;
    }




    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }


    /**
     * get请求
     */
    public void getJSONObject(String url, Map parameters, Observer<JSONObject> observer) {
        if (parameters == null) { //无参
            apiService.executeJSONObjectGet1(url)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } else if (parameters != null) {//有参
            apiService.executeJSONObjectGet2(url, parameters)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }
    }


    /**
     * post请求
     */
    public void postJSONObject(String url, Map parameters, Observer<JSONObject> observer) {
        if (parameters == null) { // 无参
            apiService.executeJSONObjectPost1(url)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } else {//有参
            apiService.executeJSONObjectPost2(url, parameters)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }
    }


    /**
     * get请求
     */
    public void getResponseBody(String url, Map parameters, Observer<ResponseBody> observer) {
        if (parameters == null) { //无参
            apiService.executeResponseBodyGet1(url)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } else if (parameters != null) {//有参
            apiService.executeResponseBodyGet2(url, parameters)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }
    }


    /**
     * post请求
     */
    public void postResponseBody(String url, Map parameters, Observer<ResponseBody> observer) {
        if (parameters == null) { // 无参
            apiService.executeResponseBodyPost1(url)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } else {//有参
            apiService.executeResponseBodyPost2(url, parameters)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }
    }

    /**
     * post请求
     */
    public void postLogin(JSONObject jsonObject, Observer<JSONObject> observer) {

        Observable<JSONObject> loginObservable = loginApiService.login(jsonObject.toString());
        loginObservable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }


    /**
     * 上传
     */
    public void upload(String url, RequestBody requestBody, Observer<ResponseBody> observer) {
        apiService.upLoadFile(url, requestBody)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(observer);
    }


    /**
     * 下载
     */
    public void download(String url, Context context, final CallBack callBack) {
        apiService.downloadFilePOST(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new DownObserver<ResponseBody>(context, callBack));
    }


    public static <T> T execute(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

        return null;
    }


}

