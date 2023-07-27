package com.rt.lib_connect.config

import android.app.Application
import android.content.Context
import okhttp3.Interceptor

/**
 * okhttp配置类,这个类主要的作用就是配置相关的网络请求参数
 */
class OkHttpConfig private constructor(
    var url: String
) {

    var connectTimeOut: Long = 0L
    var readTimeOut: Long = 0L
    var writeTimeOut: Long = 0L
    var interceptorList: List<Interceptor> = mutableListOf()

    private constructor(builder: Builder) : this(
        url = builder.url
    ) {
        connectTimeOut = builder.connectTimeOut
        readTimeOut = builder.readTimeOut
        writeTimeOut = builder.writeTimeOut
        interceptorList = builder.interceptorList
    }

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var url: String = ""
        var connectTimeOut: Long = 0L
        var readTimeOut: Long = 0L
        var writeTimeOut: Long = 0L
        var interceptorList: List<Interceptor> = mutableListOf()

        fun build() = OkHttpConfig(this)
    }
}