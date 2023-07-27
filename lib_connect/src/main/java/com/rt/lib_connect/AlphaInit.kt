package com.rt.lib_connect

import android.app.Application
import android.os.Looper
import android.util.Log
import com.rt.lib_connect.config.OkHttpConfig
import com.rt.lib_foundation.InitModelProject

class AlphaInit : InitModelProject {

    private val okHttpConfig = OkHttpConfig.build {
        url = "https://www.wanandroid.com"
    }

    override fun init(application: Application) {
        Log.e("TAG======>", "init: NetWork" )
        OkHttpManager.init(okHttpConfig)
    }
}