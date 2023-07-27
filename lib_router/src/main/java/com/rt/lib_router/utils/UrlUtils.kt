package com.rt.lib_router.utils

import android.net.Uri
import android.text.TextUtils
import android.util.Log
import java.net.URL

/**
 * url工具类
 */
object UrlUtils {

    private const val urlRegex = "[a-zA-z]+://([^\\s?]*)(?:\\?[^\\s]*)?"

    /**
     * 规范url的方法
     * 因为很多url不是以http://开头的
     * 这里看看规则吧!!!
     */
    private fun stringToURL(url: String): URL? {
        return if (url.isEmpty() || !url.contains("://")) {
            null
        } else try {
            URL("https" + url.substring(url.indexOf("://")))
        } catch (ex: Exception) {
            null
        }
    }

    /**
     * 根据Url获取除参数之外的
     *
     * @param url 跳转的url
     * @return 移除参数后的url
     */
    fun getUrlKeyFormUrl(url: String): String {

        //如果返回为空的情况下就直接返回空字符串了.
//        stringToURL(url) ?: return ""
        if (url.isEmpty()) return ""

        //通过正则获取出相关的列表
        val specsUrls: List<String> = RegexUtils.getMatchList(url, urlRegex, false)

        if (specsUrls.isEmpty()) {
            return ""
        }

        val specsUrl = specsUrls[0]
        Log.e("TAG", "getUrlKeyFormUrl: $specsUrl")
        return specsUrl
    }

    /**
     * 根据Url获取相关参数
     */
    fun getUrlParams(url: String): Map<String, String?> {

        val paramsMap: MutableMap<String, String?> = mutableMapOf()

        if (TextUtils.isEmpty(url)) {
            return paramsMap
        }

        val uri = Uri.parse(url)

        uri.queryParameterNames?.forEach { key ->
            paramsMap[key] = uri.getQueryParameter(key)
        }

        return paramsMap
    }
}