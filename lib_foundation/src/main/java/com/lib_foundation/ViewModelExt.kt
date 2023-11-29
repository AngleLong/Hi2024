package com.lib_foundation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun <T> loadDataWithState(
    scope: CoroutineScope,
    dataState: DataState<T>,
    loader: suspend (ioScope: CoroutineScope) -> T
) {
    scope.launch {
        dataState.run {
            if (loading) {
                return@run
            }

            loading = true

            //网络请求开始
            networkState.postValue(NetworkState(Status.RUNNING))

            try {
                val resultData = withContext(Dispatchers.IO) {
                    //发送网络请求
                    loader(this)
                }
                data.postValue(resultData)
                networkState.postValue(NetworkState(Status.SUCCESS))
            } catch (e: Exception) {
                networkState.postValue(NetworkState(Status.FAILED, e.toString()))
            } finally {
                loading = false
            }
        }
    }
}

/**
 * viewModel中需要刷新时使用网络请求
 */
fun <T> ViewModel.loadDataWithState(dataState: DataState<T>, loader: suspend (ioScope: CoroutineScope) -> T) {
    loadDataWithState(
        scope = viewModelScope,
        dataState = dataState,
        loader = loader
    )
}

fun <T> loadDataWithNormalState(
    scope: CoroutineScope,
    dataState: NormalDataState<T>,
    loader: suspend (ioScope: CoroutineScope) -> T
) {
    scope.launch {
        dataState.run {
            if (loading) {
                return@run
            }

            loading = true

            //网络请求开始
            networkState.postValue(NetworkState(Status.RUNNING))

            try {
                val resultData = withContext(Dispatchers.IO) {
                    //发送网络请求
                    loader(this)
                }
                data.postValue(resultData)
                networkState.postValue(NetworkState(Status.SUCCESS))
            } catch (e: Exception) {
                networkState.postValue(NetworkState(Status.FAILED, e.toString()))
            } finally {
                loading = false
            }
        }
    }
}

/**
 * viewModel中不需要刷新时使用网络请求
 */
fun <T> ViewModel.loadDataWithNormalState(dataState: NormalDataState<T>, loader: suspend (ioScope: CoroutineScope) -> T) {
    loadDataWithNormalState(
        scope = viewModelScope,
        dataState = dataState,
        loader = loader
    )
}