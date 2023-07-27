package com.rt.lib_connect.interceptor

import android.text.TextUtils
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * 提交jsonBody添加公共请求参数
 */
class PostJsonParamsInterceptor : Interceptor {

    private val methodPost = "POST"

    override fun intercept(chain: Interceptor.Chain): Response {
        //获取旧的请求
        var request = chain.request()

        //如果请求是get请求的情况下进行拦截,处理公共请求参数

        //如果请求是get请求的情况下进行拦截,处理公共请求参数
        if (TextUtils.equals(request.method, methodPost) && request.body is FormBody) {
            val paramsMap: MutableMap<String, String> = mutableMapOf()

            //获取body请求
            val formBody: FormBody = request.body as FormBody

            //将原始数据添加到map中
            for (i in 0 until formBody.size) {
                paramsMap[formBody.encodedName(i)] = formBody.encodedValue(i)
            }
            //追加新的参数
            paramsMap["key1"] = "value1"
            paramsMap["key2"] = "value2"
            paramsMap["key3"] = "value3"

            //将数据转换成指定的字符串
            val gson = Gson()
            val jsonParams: String = gson.toJson(paramsMap)

            //构建新的body对象
            val requestBody = jsonParams.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            //构造新的请求体
            request = request.newBuilder().post(requestBody).build()
        }

        return chain.proceed(request)
    }

}