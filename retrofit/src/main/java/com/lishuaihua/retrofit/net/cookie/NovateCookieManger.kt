package com.lishuaihua.retrofit.net.cookie

import android.content.Context
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class NovateCookieManger(private val mContext: Context) : CookieJar {
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        if (cookies != null && cookies.size > 0) {
            for (item in cookies) {
                cookieStore!!.add(url, item)
            }
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookieStore!!.get(url)
    }

    companion object {
        private const val TAG = "NovateCookieManger"
        private var cookieStore: PersistentCookieStore? = null
    }

    /**
     * Mandatory constructor for the NovateCookieManger
     */
    init {
        if (cookieStore == null) {
            cookieStore = PersistentCookieStore(mContext)
        }
    }
}