package com.lib_router

import android.app.Application
import android.os.Looper
import com.lib_foundation.InitModelProject

class AlphaInit : InitModelProject {

    override fun init(application: Application) {
//        Looper.myQueue().addIdleHandler {
//            RouterManager.init(application, true)
//            false
//        }
    }
}