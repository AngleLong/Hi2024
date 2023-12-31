package com.lib_login.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.lib_common.BaseActivity
import com.lib_login.databinding.ActivitySplashBinding
import com.rt.lib_router.center.openLogin
import com.rt.lib_router.center.openMain
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class HiSplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun operationView() {

        /**
         * 在某些情况下从launcher进入app会重复打开Splash页面
         * https://issuetracker.google.com/issues/64108432
         * https://stackoverflow.com/a/42125549/7777497
         */
        if (!isTaskRoot) {
            val action: String? = intent.action
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && !action.isNullOrEmpty() && action == Intent.ACTION_MAIN) {
                finish()
                return
            }
        }

        //闪屏主题
        installSplashScreen()
    }

    override fun bindAction() {
        lifecycleScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                Thread.sleep(3000)
            }

            openMain()
        }
    }
}