package com.rt.lib_connect.interceptor

import android.text.TextUtils
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Get请求添加公共请求参数
 */
class GetParamsInterceptor : Interceptor {

    private val methodGet = "GET"

    override fun intercept(chain: Interceptor.Chain): Response {

        //获取旧的请求
        var request = chain.request()

        //如果请求是get请求的情况下进行拦截,处理公共请求参数
        if (TextUtils.equals(request.method, methodGet)) {
            val build = request.url.newBuilder()
                .addQueryParameter("key1", "value1")
                .addQueryParameter("key2", "value2")
                .addQueryParameter("key3", "value3")
                .build()
            request = request.newBuilder().url(build).build()
        }

        return chain.proceed(request)
    }
}