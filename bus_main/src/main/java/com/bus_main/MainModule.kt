package com.bus_main

import com.lib_connect.OkHttpManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    @Singleton
    fun provideGitHubService(): MainApi = OkHttpManager.defaultApiService(MainApi::class.java)
}