package com.rt.lib_router.utils

import java.util.regex.Pattern

object RegexUtils {

    fun getMatchList(str: String, reg: String, isCaseInsensitive: Boolean): List<String> {

        val result = ArrayList<String>()
        val pattern: Pattern = if (isCaseInsensitive) {
            //编译正则表达式,忽略大小写
            Pattern.compile(reg, Pattern.CASE_INSENSITIVE)
        } else {
            //编译正则表达式,大小写敏感
            Pattern.compile(reg)
        }
        // 指定要匹配的字符串
        val matcher = pattern.matcher(str)
        //此处find（）每次被调用后，会偏移到下一个匹配
        while (matcher.find()) {
            //获取当前匹配的值
            result.add(matcher.group())
        }
        result.trimToSize()
        return result
    }
}