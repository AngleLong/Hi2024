package com.rt.lib_router.center

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter
import com.rt.lib_router.service.LoginService

//测试
const val TESTROUTERSTR = "/test/secondActivity"
fun bindTest(): Postcard = ARouter.getInstance().build(TESTROUTERSTR)

// webView网页
const val WEBROUTERSTR = "/web/defaultWebActivity"
fun bindDefaultWeb(): Postcard = ARouter.getInstance().build(WEBROUTERSTR)
fun openDefaultWeb(urlParams: Map<String, String?>) {
    for ((key, value) in urlParams) {
        bindDefaultWeb().withString(key, value)
    }
    bindDefaultWeb().navigation()
}


//--------------登录模块----------
const val LOGINGROUP = "/login"
const val LOGINROUTER = "$LOGINGROUP/loginActivity"

fun bindLogin(): Postcard = ARouter.getInstance().build(LOGINROUTER)
fun openLogin(urlParams: Map<String, String?> = mutableMapOf()) {
    for ((key, value) in urlParams) {
        bindLogin().withString(key, value)
    }
    bindLogin().navigation()
}

const val LOGINSERVICE = "$LOGINGROUP/loginService"
fun getLoginService(): LoginService = ARouter.getInstance().navigation(LoginService::class.java)

//--------------主页面模块----------
const val MAINGROUP = "/main"
const val MAINROUTER = "$MAINGROUP/loginActivity"
fun bindMain(): Postcard = ARouter.getInstance().build(MAINROUTER)
fun openMain(urlParams: Map<String, String?> = mutableMapOf()) {
    for ((key, value) in urlParams) {
        bindMain().withString(key, value)
    }
    bindMain().navigation()
}