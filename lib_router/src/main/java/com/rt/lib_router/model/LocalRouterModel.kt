package com.rt.lib_router.model

import java.io.Serializable

/**
 * 本地映射出来的路由集合
 * 其实这里面的结构可以和服务器协定
 */
data class LocalRouterModel(
    var list: MutableList<RouterModel> = mutableListOf(),
) : Serializable