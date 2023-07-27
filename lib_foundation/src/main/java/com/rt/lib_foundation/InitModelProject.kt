package com.rt.lib_foundation

import android.app.Application

/**
 * 用于模块的初始化
 * 每个模块要是有需要初始化的资源在AndroidManifest的定义meta
 */
interface InitModelProject {
    fun init(application: Application)
}