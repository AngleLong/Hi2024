package com.rt.lib_router.service

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * 登陆暴露的接口状态
 */
interface LoginService : IProvider {

    /**
     * 是否登陆
     */
    fun isLogin(): Boolean
}