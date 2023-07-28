package com.rt.lib_longin

import android.app.Application
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.rt.lib_foundation.InitModelProject

class AlphaInit : InitModelProject {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun init(application: Application) {
        // TODO: 这里可以进行一些相关的初始化工作
    }
}