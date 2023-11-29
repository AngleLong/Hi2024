package com.lib_login.viewmodel

import androidx.lifecycle.ViewModel
import com.lib_connect.model.Empty
import com.lib_foundation.DataState
import com.lib_foundation.loadDataWithState
import com.lib_login.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * 登陆的VieModel
 */
@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    val register: DataState<Empty> = DataState()

    fun userRegister(name: String, psd: String, psdAgain: String) {
        loadDataWithState(
            register,
            loader = {
                repository.userRegister(name, psd)
            }
        )
    }


//    fun userRegisterFlow(name: String, psd: String) = flow<Empty> {
//        val userRegister = repository.userRegister(name, psd)
//        emit(userRegister)
//    }


}