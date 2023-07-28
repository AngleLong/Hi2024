package com.rt.lib_longin

import com.rt.lib_connect.OkHttpManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 用于登陆模块中的注册
 */
@Module
@InstallIn(SingletonComponent::class)
class LoginModule {

    @Provides
    @Singleton
    fun provideDefaultApi(): LoginApi = OkHttpManager.defaultApiService(LoginApi::class.java)
}