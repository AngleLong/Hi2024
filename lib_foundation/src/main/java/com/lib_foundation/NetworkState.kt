package com.lib_foundation


enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

/**
 * 网络状态的类
 * 如果Status为FAILED的情况下需要配置msg信息
 */
data class NetworkState(
    val status: Status,
    val msg: String = ""
)