package com.angle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.rt.lib_connect.OkHttpManager
import com.rt.lib_connect.config.OkHttpConfig
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val api = OkHttpManager.defaultApiService(MainApi::class.java)

        lifecycleScope.launch {
            try {
                val result = api.register("zhangSan", "123456", "123456")
                Log.e("TAG", "onCreate: $result")
            } catch (e: Exception) {
                Log.e("TAG", "onCreate: $e")
            }
        }
    }
}