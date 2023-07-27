package com.angle

import androidx.lifecycle.ViewModel
import com.rt.lib_foundation.DataState
import com.rt.lib_foundation.loadDataWithState

class MainViewModel(val api: MainApi) : ViewModel() {


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
}