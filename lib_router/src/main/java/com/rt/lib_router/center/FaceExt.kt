package com.rt.lib_router.center

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter

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

const val LOGINROUTER = "/login/LoginActivity"
fun bindLogin(): Postcard = ARouter.getInstance().build(LOGINROUTER)
fun openLogin(urlParams: Map<String, String?> = mutableMapOf()) {
    for ((key, value) in urlParams) {
        bindLogin().withString(key, value)
    }
    bindLogin().navigation()
}