package com.rt.lib_longin.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.rt.lib_router.center.LOGINSERVICE
import com.rt.lib_router.service.LoginService

@Route(path = LOGINSERVICE, name = "登陆相关服务")
class LoginServiceImpl : LoginService {
    override fun isLogin(): Boolean {
        return true
    }

    override fun init(context: Context?) {

    }
}