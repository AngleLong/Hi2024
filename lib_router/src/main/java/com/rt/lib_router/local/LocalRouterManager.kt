package com.rt.lib_router.local

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.google.gson.Gson
import com.rt.lib_router.center.TESTROUTERSTR
import com.rt.lib_router.center.bindTest
import com.rt.lib_router.model.LocalRouterModel

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * 本地路由处理
 * 这里的本地路由其实依赖配置文件生成一个指定的Map集合用来保存本地的信息
 * 这里可以考虑把本地的map映射在数据库中,这样可以选择在远端更新本地的数据映射
 */
object LocalRouterManager {

    /**
     * 本地映射出来的Map
     */
    val postcardMap: MutableMap<String, Postcard> = mutableMapOf()

    /**
     * 读取本地配置信息
     */
    fun loadRouterConfig(context: Context) {
        val stringBuilder = StringBuilder()
        val assets = context.assets
        try {
            val bf = BufferedReader(InputStreamReader(assets.open("")))
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val localResult = stringBuilder.toString()

        if (localResult.isNotEmpty()) {
            initPostcardMap(localResult)
        }
    }

    private fun initPostcardMap(localResult: String) {
        val localRouterModel = Gson().fromJson(localResult, LocalRouterModel::class.java)
        localRouterModel.list.forEach { item ->
            postcardMap[item.url] = localPostcardKey(item.path)
        }
    }

    private fun localPostcardKey(key: String): Postcard = when (key) {
        TESTROUTERSTR -> {
            bindTest()
        }
        else -> {
            bindTest()
        }
    }


}