package com.rt.lib_foundation

import androidx.lifecycle.MutableLiveData

/**
 * 需要刷新和重试的网络请求
 */
data class DataState<T>(
    // the LiveData of paged lists for the UI to observe
    val data: MutableLiveData<T> = MutableLiveData(),
    // represents the network request status to show to the user
    val networkState: MutableLiveData<NetworkState> = MutableLiveData(),
    var loading: Boolean = false,
    // represents the refresh status to show to the user. Separate from networkState, this
    // value is importantly only when refresh is requested.
    val refreshState: MutableLiveData<NetworkState> = MutableLiveData(),
    // refreshes the whole data and fetches it from scratch.
    var refresh: () -> Unit = {},
    // retries any failed requests.
    var retry: () -> Unit = {}
)

/**
 * 不需要刷新和重试的网络请求
 */
data class NormalDataState<T>(
    // the LiveData of paged lists for the UI to observe
    val data: MutableLiveData<T> = MutableLiveData(),
    // represents the network request status to show to the user
    val networkState: MutableLiveData<NetworkState> = MutableLiveData(),
    var loading: Boolean = false
)