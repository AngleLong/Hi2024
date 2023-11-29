package com.bus_main.ui

import com.alibaba.android.arouter.facade.annotation.Route
import com.bus_main.databinding.ActivityMainBinding
import com.lib_common.BaseActivity
import com.rt.lib_router.center.MAINROUTER
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = MAINROUTER)
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun operationView() {
    }

    override fun bindAction() {
    }
}