package com.rt.lib_connect.interceptor

import android.text.TextUtils
import okhttp3.*

/**
 * Post公共请求参数
 */
class PostParamsInterceptor : Interceptor {

    private val methodPost = "POST"

    override fun intercept(chain: Interceptor.Chain): Response {
        //获取旧的请求
        var request = chain.request()

        //如果请求是get请求的情况下进行拦截,处理公共请求参数

        //如果请求是get请求的情况下进行拦截,处理公共请求参数
        if (TextUtils.equals(request.method, methodPost) && request.body is FormBody) {

            // 构造新的请求表单
            val newBuilder =  FormBody.Builder()
            val formBody = request.body as FormBody?

            //将原始数据添加到builder中
            formBody?.let {
                for (i in 0 until formBody.size) {
                    newBuilder.addEncoded(it.encodedName(i), it.encodedValue(i))
                }
                //追加新的参数
                newBuilder.addEncoded("key1", "value1")
                newBuilder.addEncoded("key2", "value2")
                newBuilder.addEncoded("key3", "value3")
            }

            //构造新的请求体
            request = request.newBuilder().post(newBuilder.build()).build()
        }

        return chain.proceed(request)
    }
}