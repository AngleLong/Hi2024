package com.lib_foundation

import android.app.Application
import com.orhanobut.logger.DiskLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.lib_foundation.logger.MyCsvFormatStrategy


class AlphaInit : InitModelProject {
    override fun init(application: Application) {

        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)
            .methodCount(0)
            .methodOffset(7)
            .tag("YouAngle")
            .build()

        Logger.addLogAdapter(
            DiskLogAdapter(
                MyCsvFormatStrategy
                    .newBuilder()
                    .context(application)
                    .tag(application.packageName)
                    .build()
            )
        )
    }
}