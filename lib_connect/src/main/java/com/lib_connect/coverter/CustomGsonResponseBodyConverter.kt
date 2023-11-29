package com.lib_connect.coverter

import android.util.Log
import com.google.gson.Gson
import com.lib_connect.exception.ApiException
import okhttp3.ResponseBody
import okio.IOException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Converter
import java.lang.reflect.Type

/**
 * 自定义相应的转换器
 */
class CustomGsonResponseBodyConverter<T>(private val gson: Gson, private val type: Type) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        //响应结果
        //{
        //  "success": true,
        //  "code": "Operation.Success",
        //  "message": "Operation.Success",
        //  "requestId":"1655104186558$e44d6012-cd7d-e6b0-3522-8d14be15ad8c",
        //  "data": null
        //}
        //因为成功的时候,有的没有返回值,这样情况直接使用Empty进行站位,上层应该明确

        //{"data":null,"errorCode":-1,"errorMsg":"用户名已经被注册！"}

        val string = value.string()
        Log.e("TAG", "convert: $string" )
        try {
            val requestJson = JSONObject(string)
            val resultCode = requestJson.optInt("errorCode")
            if (0 == resultCode) {
                val datJson = requestJson.optString("errorMsg")

                Log.e("TAG", "convert: $type")

                // TODO: 这里看看如果是空的情况下是否要返回对象
                if(datJson is String){
                    
                }

                return gson.fromJson(datJson, type)
            } else {
                throw ApiException(requestJson.optInt("errorCode"), requestJson.optString("errorMsg"))
            }
        } catch (e: JSONException) {
            throw IOException(e.toString())
        } finally {
            value.close()
        }
    }
}