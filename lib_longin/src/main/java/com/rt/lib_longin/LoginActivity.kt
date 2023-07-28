package com.rt.lib_longin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.rt.lib_foundation.Status
import com.rt.lib_router.center.LOGINROUTER

/**
 * 登陆页面
 */
@AndroidEntryPoint
@Route(path = LOGINROUTER)
class LoginActivity : AppCompatActivity() {

    //创建loginViewModel实例
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginViewModel.userRegister("zhangSan", "123456", "123456")

        loginViewModel.register.data.observe(this) {
            Log.e("TAG======>", "onCreate: $it")
        }

        loginViewModel.register.networkState.observe(this) { it ->
            Log.e("TAG======>", "onCreate: ${it.status}")
            if (it.status == Status.FAILED) {
                Log.e("TAG======>", "onCreate: ${it.msg}")
            }
        }
    }
}