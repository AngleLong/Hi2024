package com.rt.lib_longin

import javax.inject.Inject

/**
 * 这个类主要是管理中间的数据层
 */
class LoginRepository @Inject constructor(private val api: LoginApi) {

    /**
     * 注册接口
     * 这里可以结合数据库进行处理,上层不论数据是从哪里来的.
     * 这里其实也可以封装一个flow
     */
    suspend fun userRegister(name: String, psd: String): String = api.register(name, psd, psd)
}