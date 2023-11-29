package com.lib_login.module

import com.lib_connect.OkHttpManager
import com.lib_login.LoginApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 用于登陆模块中的注册
 * 这里注册的单例模式的样式
 */
@Module
@InstallIn(SingletonComponent::class)
class LoginModule {

    @Provides
    @Singleton
    fun provideDefaultApi(): LoginApi = OkHttpManager.defaultApiService(LoginApi::class.java)
}