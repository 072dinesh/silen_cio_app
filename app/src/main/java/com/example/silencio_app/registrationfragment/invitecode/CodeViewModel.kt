package com.example.silencio_app.registrationfragment.invitecode

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.silencio_app.R
import com.example.silencio_app.data.source.AuthDataSource
import com.example.silencio_app.registrationfragment.model.VerifyInviteCodeRequestModelX
import com.example.silencio_app.registrationfragment.response.VerifyInviteCodeReques
import com.example.silencio_app.util.BaseViewModel
import com.example.silencio_app.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CodeViewModel @Inject constructor(application: Application,
private val authDataSource: AuthDataSource,
):BaseViewModel(application) {

val code = MutableStateFlow("")

    val iscodevail = code.map {

        it.isNotEmpty()
    }

    private val _allUsers = MutableLiveData<NetworkResult<VerifyInviteCodeReques>>()
    val allUsers: LiveData<NetworkResult<VerifyInviteCodeReques>>
        get() = _allUsers
    //
    var mContext = application


    fun getPostdatainvaidcode() {
        viewModelScope.launch {
            if(isConnected()){
                _allUsers.value = NetworkResult.Loading()
                val response = authDataSource.verifyInviteCode(VerifyInviteCodeRequestModelX(code.value))
                _allUsers.value = handleResponse(response)
            } else {
                _allUsers.value = NetworkResult.Error(
                    mContext.getString(R.string.no_Internet)
                )
            }
        }
    }



}