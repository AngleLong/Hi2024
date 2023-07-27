package com.rt.lib_connect.config

import com.rt.lib_connect.OkHttpManager
import com.rt.lib_connect.interceptor.CustomLogInterceptor
import com.rt.lib_connect.interceptor.PostJsonParamsInterceptor
import com.rt.lib_connect.interceptor.PostParamsInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * OkHttp的构建方法
 * 主要是为了创建OkHttp对象
 */
object OkHttpEngine {

    private val config = OkHttpManager.okHttpConfig

    val okHttpClient = createOkHttpBuilder().build()

    private fun createOkHttpBuilder(): OkHttpClient.Builder = OkHttpClient.Builder().apply {
        connectTimeout(config.connectTimeOut, TimeUnit.SECONDS)
        readTimeout(config.readTimeOut, TimeUnit.SECONDS)
        writeTimeout(config.writeTimeOut, TimeUnit.SECONDS)

        //添加外部的拦截器
        val interceptorList = config.interceptorList
        for (interceptor in interceptorList) {
            addInterceptor(interceptor)
        }

        //添加公共请求参数拦截器
        addInterceptor(PostParamsInterceptor())

        //添加日志拦截器
        addInterceptor(CustomLogInterceptor())

    }
}