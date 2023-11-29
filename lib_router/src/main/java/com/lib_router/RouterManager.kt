package com.rt.lib_router

import android.app.Application
import android.text.TextUtils
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.rt.lib_router.center.openDefaultWeb
import com.rt.lib_router.local.LocalRouterManager
import com.rt.lib_router.utils.UrlUtils

/**
 * Router的初始化
 */
object RouterManager {

    fun init(application: Application, isDebug: Boolean) {
        //1. 初始化ARouter
        if (isDebug) {
            // 这两行必须写在init之前，否则这些配置在init过程中将无效
            // 打印日志
            ARouter.openLog()
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug()
        }
        ARouter.init(application)


        //2. 更新本地映射关系
        LocalRouterManager.loadRouterConfig(application)
    }

    /**
     * 全局跳转的方法
     */
    fun navigate(url: String) {
        if (TextUtils.isEmpty(url)) {
            Log.e("TAG", "navigate: 传入的Url为空")
            return
        }

        //获取url中携带的参数
        val urlParams = UrlUtils.getUrlParams(url)

        //获取url中的key用来匹配相关的Postcard
        val key = UrlUtils.getUrlKeyFormUrl(url).replace("http://", "").replace("https://", "")

        if (key.isEmpty()) {
            //这里为空应该是从url中没办法获取到对应的跳转地址,直接认跳转到web页面
            openDefaultWeb(urlParams)
        } else {
            val postcard = LocalRouterManager.postcardMap[key]

            for ((keyStr, value) in urlParams) {
                postcard?.withString(keyStr, value)
            }

            postcard?.navigation()
        }
    }
}

//inline fun <T> Boolean.yes(block: () -> T) =
//    when {
//        this -> {
//            WithData(block())
//        }
//        else -> {
//            Otherwise
//        }
//    }

inline fun <T> String.no(block: () -> T) {
    if (TextUtils.isEmpty(this)) {
        block()
    }
}



