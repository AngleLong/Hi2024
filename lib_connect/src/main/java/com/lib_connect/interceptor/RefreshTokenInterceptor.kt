package com.lib_connect.interceptor

import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject

class RefreshTokenInterceptor : Interceptor {

//    val global = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun intercept(chain: Interceptor.Chain): Response {

        //原始请求信息
        val request = chain.request()

        //原始响应结果
        val response = chain.proceed(request)

        //校验token是否过期
        if (isTokenExpired(response)) {
            //过期的情况下,要重新创建响应体
            val token = syncRequestToken()?.apply {
                //todo 这里保存一下新的token
            }

            val newRequest = request.newBuilder()
                .addHeader("token", token ?: "")
                .build()

            return chain.proceed(newRequest)
        }

        return response
    }

    private fun isTokenExpired(response: Response): Boolean {
        // TODO: 这里其实应该根据服务器返回的错误码判断token过期
        return false
    }

    /**
     * 同步请求Token,这个token看是从哪里获取
     */
    @Synchronized
    private fun syncRequestToken(): String? {
        val request = Request.Builder()
            .url("")
            .build()

        val okHttpClient = OkHttpClient.Builder().build()

        val response = okHttpClient.newCall(request).execute()

        if (!TextUtils.isEmpty(response.body?.string())) {

            val resultJson = response.body?.string()?.let {
                return@let JSONObject(it)
            }

            //这里直接获取token返回就行了.
            return resultJson?.optString("accessToken")
        }
        return null
    }
}