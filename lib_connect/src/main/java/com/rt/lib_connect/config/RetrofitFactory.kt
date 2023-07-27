package com.rt.lib_connect.config

import com.rt.lib_connect.OkHttpManager
import com.rt.lib_connect.coverter.CustomGsonConverterFactory
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit 工厂类
 * 主要是为了创建相关的Retrofit对象
 */
object RetrofitFactory {

    private  var defaultRt: Retrofit

    private val builder = Retrofit.Builder().apply {
        client(OkHttpEngine.okHttpClient)

//        addConverterFactory(GsonConverterFactory.create())
        addConverterFactory(CustomGsonConverterFactory.create())
        //这个看看如果是livedata或flow是否能省略
        addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
    }

    init {
        //默认的Retrofit
        builder.baseUrl(OkHttpManager.okHttpConfig.url)
        defaultRt = builder.build()

        // TODO: 这里如果是多个网址请求的话,可以创建多个Retrofit
    }

    /**
     * 获取默认的Retrofit对象
     */
    fun getDefault() = defaultRt
}