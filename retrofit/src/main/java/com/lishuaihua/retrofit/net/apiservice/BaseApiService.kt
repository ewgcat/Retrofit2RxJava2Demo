package com.lishuaihua.retrofit.net.apiservice

import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface BaseApiService {
    /** */ /*GET 请求  无参*/
    @GET
    fun executeJSONObjectGet1(
            @Url url: String?
    ): Observable<JSONObject?>?

    /*GET 请求  有参*/
    @GET
    fun executeJSONObjectGet2(
            @Url url: String?,
            @QueryMap maps: Map<String?, String?>?
    ): Observable<JSONObject?>?

    /*POST 请求 无参*/
    @POST
    fun executeJSONObjectPost1(
            @Url url: String?
    ): Observable<JSONObject?>?

    /*POST 请求 有参*/
    @POST
    fun executeJSONObjectPost2(
            @Url url: String?,
            @QueryMap maps: Map<String?, String?>?
    ): Observable<JSONObject?>?

    /** */ /*GET 请求  无参*/
    @GET
    fun executeResponseBodyGet1(
            @Url url: String?
    ): Observable<ResponseBody?>?

    /*GET 请求  有参*/
    @GET
    fun executeResponseBodyGet2(
            @Url url: String?,
            @QueryMap maps: Map<String?, String?>?
    ): Observable<ResponseBody?>?

    /*POST 请求 无参*/
    @POST
    fun executeResponseBodyPost1(
            @Url url: String?
    ): Observable<ResponseBody?>?

    /*POST 请求 有参*/
    @POST
    fun executeResponseBodyPost2(
            @Url url: String?,
            @QueryMap maps: Map<String?, String?>?
    ): Observable<ResponseBody?>?

    /** */ /*GET 请求 下载*/
    @Streaming
    @GET
    fun downloadFileGET(
            @Url fileUrl: String?
    ): Observable<ResponseBody?>?

    /*POST 请求 下载*/
    @Streaming
    @POST
    fun downloadFilePOST(
            @Url fileUrl: String?
    ): Observable<ResponseBody?>?

    /*POST 请求 上传单个文件*/
    @Multipart
    @POST("{url}")
    fun upLoadFile(
            @Path("url") url: String?,
            @Part("image\"; filename=\"image.jpg") requestBody: RequestBody?
    ): Observable<ResponseBody?>?

    /*POST 请求 上传文件*/
    @POST("{url}")
    fun uploadFiles(
            @Path("url") url: String?,
            @Part("filename") description: String?,
            @PartMap maps: Map<String?, RequestBody?>?
    ): Call<ResponseBody?>?
}