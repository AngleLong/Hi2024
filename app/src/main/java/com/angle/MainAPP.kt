package com.angle

import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.rt.lib_connect.OkHttpManager
import com.rt.lib_connect.config.OkHttpConfig
import com.rt.lib_foundation.InitModelProject
import com.rt.lib_router.RouterManager

private const val metaKey = "AlphaInit"

class MainAPP : Application() {


    override fun onCreate() {
        super.onCreate()

        //读取所有的AlphaInit文件进行相关初始化
        readAlphaInit()
    }


    /**
     * 读取所有的Init文件
     */
    private fun readAlphaInit() {
        val metadata = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getApplicationInfo(
                packageName,
                PackageManager.ApplicationInfoFlags.of(PackageManager.GET_META_DATA.toLong())
            )
        } else {
            packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        }.metaData

        //获取到metaData定义的内容集合
        metadata.keySet()?.filter { key ->
            metadata.getString(key) == metaKey
        }?.forEach { className ->
            Log.e("TAG======>", "readAlphaInit: $className")
            parseModule(className).init(this@MainAPP)
        }
    }
}


private fun parseModule(className: String): InitModelProject {
    try {
        return (Class.forName(className).newInstance() as InitModelProject)
    } catch (e: ClassNotFoundException) {
        throw IllegalArgumentException(e)
    } catch (e: InstantiationException) {
        throw RuntimeException(e)
    } catch (e: IllegalAccessException) {
        throw RuntimeException(e)
    }
}