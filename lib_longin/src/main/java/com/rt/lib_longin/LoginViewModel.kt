package com.rt.lib_longin

import androidx.lifecycle.ViewModel
import com.rt.lib_foundation.DataState
import com.rt.lib_foundation.loadDataWithState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 登陆的VieModel
 */
@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

     val register: DataState<String> = DataState()

    fun userRegister(name: String, psd: String, psdAgain: String) {
        loadDataWithState(
            register,
            loader = {
                repository.userRegister(name, psd)
            }
        )
    }
}