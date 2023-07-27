package com.rt.lib_connect.exception

/**
 * App中规范的异常
 */
class ApiException(var code: Int,  msg: String) : RuntimeException(msg)