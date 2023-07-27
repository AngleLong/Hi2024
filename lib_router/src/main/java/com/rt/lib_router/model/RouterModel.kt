package com.rt.lib_router.model

import java.io.Serializable

/**
 * 本地映射的实体类
 */
data class RouterModel(
    var name: String = "",
    var path: String = "",
    val url: String = ""
) : Serializable
