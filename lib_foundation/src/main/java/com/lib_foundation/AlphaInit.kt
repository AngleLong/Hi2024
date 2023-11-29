package com.lib_foundation

import android.app.Application
import com.lib_foundation.logger.LoggerManager
import com.lib_foundation.logger.MyCsvFormatStrategy


class AlphaInit : InitModelProject {
    override fun init(application: Application) {
        //初始化日志类
        LoggerManager.initLogger(application)
    }
}