package com.rt.lib_connect.interceptor

import android.util.Log
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.net.URLEncoder
import java.nio.charset.Charset


/**
 * 自定义日志拦截器
 */
class CustomLogInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        printInfo(request, response)
        return response
    }

    private fun printInfo(request: Request?, response: Response?) {
        if (request != null && response != null) {
            val logInfo = "\n" +
                    "自定义日志打印\n" +
                    "Request  |  Url-->:${request.method} ${request.url}\n" +
                    "Request  |  Header-->:${getRequestHeaders(request)}\n" +
                    "Request  |  Parameters-->:${getRequestParams(request)}\n" +
                    "Response |  Result-->:${getResponseText(response)}"
            Log.e("TAG======>", logInfo)
        }
    }

    private fun getResponseText(response: Response): String {
        var str = "Empty!"
        try {
            val body = response.body
            if (body != null && body.contentLength() != 0L) {
                val source = body.source()
                source.request(Long.MAX_VALUE)
                val buffer: Buffer = source.buffer
                body.contentType()?.also {
                    it.charset(Charset.forName("UTF-8"))?.also { charset ->
                        str = buffer.clone().readString(charset)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return str
    }

    private fun getRequestParams(request: Request): String {
        var str = "Empty!"
        try {
            val body = request.body
            if (body != null) {
                val buffer = Buffer()
                body.writeTo(buffer)
                body.contentType()?.also {
                    it.charset(
                        Charset.forName("UTF-8")
                    )?.also { charset ->
                        str = buffer.readString(charset)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return str
    }

    private fun getRequestHeaders(request: Request): String {
        val headers: Headers = request.headers
        return if (headers.size > 0) {
            headers.toString()
        } else {
            "Empty!"
        }
    }
}