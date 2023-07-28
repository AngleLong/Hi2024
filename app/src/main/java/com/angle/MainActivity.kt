package com.angle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.rt.lib_longin.LoginActivity
import com.rt.lib_router.center.openLogin
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@Route(path = "/main/MainActivity")
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var test: Test

//    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        test.testMethod()

//        mainViewModel.data.data.observe(this){
//            Log.e("TAG", "onCreate: $it")
//        }


//        startActivity(Intent(this,LoginActivity::class.java))
        openLogin()

//        mainViewModel.getTest()

//        lifecycleScope.launch {
//            try {
//                val result = api.register("zhangSan", "123456", "123456")
//                Log.e("TAG", "onCreate: $result")
//            } catch (e: Exception) {
//                Log.e("TAG", "onCreate: $e")
//            }
//        }
    }
}