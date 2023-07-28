package com.angle

import android.util.Log
import androidx.lifecycle.ViewModel
import com.rt.lib_foundation.DataState
import com.rt.lib_foundation.loadDataWithState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val api: MainApi) : ViewModel() {


    val data = DataState<String>()

    init {
        getData()
    }

    private fun getData() {
        loadDataWithState(
            data,
            loader = {
                api.register("张三", "123456", "123456")
            }
        )
    }


    fun getTest() {
        Log.e("TAG======>", "getTest: 执行")
    }
}