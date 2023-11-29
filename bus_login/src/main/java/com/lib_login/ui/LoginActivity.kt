package com.lib_login.ui

import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.lib_common.BaseActivity
import com.lib_login.viewmodel.LoginViewModel
import com.lib_login.databinding.ActivityLoginBinding
import com.rt.lib_router.center.LOGINROUTER
import dagger.hilt.android.AndroidEntryPoint

/**
 * 登陆页面
 */
@AndroidEntryPoint
@Route(path = LOGINROUTER)
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    //创建loginViewModel实例
    private val loginViewModel: LoginViewModel by viewModels()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
//

//        lifecycleScope.launch {
//            loginViewModel.userRegisterFlow("", "", "")
//                .catch {
//                    Log.e("TAG", "onCreate: $it" )
//                }
//                .collect {
//                    Log.e("TAG", "onCreate: $it")
//                }
//        }


//        fun test(success: (String) -> Unit) {
//            lifecycleScope.launch {
//                loginViewModel.userRegisterFlow("", "", "")
//                    .catch {
//
//                    }
//                    .collect {
//                        success.invoke(it)
////                        Log.e("TAG", "onCreate: $it")
//                    }
//            }
//        }

//        //调用接口
//        loginViewModel.userRegister("zhangSan", "123456", "123456")
//
//        //监听回调
//        loginViewModel.register.data.observe(this) {
//            Log.e("TAG======>", "onCreate: $it")
//        }
//
//        //监听网络
//        loginViewModel.register.networkState.observe(this) { it ->
//            Log.e("TAG======>", "onCreate: ${it.status}")
//            if (it.status == Status.FAILED) {
//                Log.e("TAG======>", "onCreate: ${it.msg}")
//            }
//        }
//    }

    override fun operationView() {

    }

    override fun bindAction() {
    }
}