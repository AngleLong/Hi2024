package com.angle

import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.lib_foundation.InitModelProject
import com.rt.lib_router.RouterManager
import dagger.hilt.android.HiltAndroidApp

private const val metaKey = "AlphaInit"

@HiltAndroidApp
class HiAPP : Application() {


    override fun onCreate() {
        super.onCreate()

        RouterManager.init(this, true)

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
            parseModule(className).init(this@HiAPP)
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