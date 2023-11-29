package com.lib_connect

import android.text.TextUtils
import com.lib_connect.config.OkHttpConfig
import com.lib_connect.config.RetrofitFactory

/**
 * OkHttp的管理类,主要是负责提供创建ApiService
 *
 * ps:OkHttpConfig中保存上下文会引起内存泄漏
 * 最后看看这里怎么获取url
 * 如果不需要上下文的情况下,可以把上下文从里面移除
 */
object OkHttpManager {

     lateinit var okHttpConfig: OkHttpConfig

    /**
     * 初始化方法
     */
    fun init(config: OkHttpConfig) {

        if (TextUtils.isEmpty(config.url)) {
            throw RuntimeException("请正确初始化 config必须配置url以确保网络请求的正常使用")
        }

        //todo 这里明确一点,如果多个url不走上面的配置就走AppConfig的Config进行配置
//        AppConfig.init(config.context)

        okHttpConfig = config
    }

    /**
     * 根据默认Url链接对外暴露默认请求的的Api接口
     */
    fun <T> defaultApiService(t: Class<T>): T = RetrofitFactory.getDefault().create(t)
}