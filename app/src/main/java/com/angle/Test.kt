package com.angle

import android.util.Log
import javax.inject.Inject

class Test @Inject constructor() {

    fun testMethod() {
        Log.e("TAG", "testMethod: 方法执行" )
    }
}