package com.example.silencio_app.registrationfragment.account

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.silencio_app.BaseResponse
import com.example.silencio_app.R
import com.example.silencio_app.data.source.AuthDataSource
import com.example.silencio_app.registrationfragment.model.VerifyNickNameRequestModel
import com.example.silencio_app.util.BaseViewModel
import com.example.silencio_app.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Timer
import javax.inject.Inject



@HiltViewModel
class AccountViewModel @Inject constructor(
    application: Application,
    private val authDataSource: AuthDataSource

    ): BaseViewModel(application) {
    val firstName = MutableStateFlow("")
    val lastName = MutableStateFlow("")
    val nickName = MutableStateFlow("")
    val tncchecked = MutableStateFlow(false)

    val isNameValid = firstName.combine(lastName) { firstName, lastName ->
        firstName.isNotEmpty() && lastName.isNotEmpty()
    }.combine(nickName){ names, nickName ->
        names && nickName.isNotEmpty()
    }.combine(tncchecked){ fields, tnc ->
        fields && tnc
    }


    private val _registerWithNickNameResponse = MutableLiveData<NetworkResult<BaseResponse>>()
    val registerWithNickNameResponse: LiveData<NetworkResult<BaseResponse>>
        get() = _registerWithNickNameResponse


    fun verifyNickName(){
        viewModelScope.launch {
            if(isConnected()){
                _registerWithNickNameResponse.value = NetworkResult.Loading()
                try {
                    val response = authDataSource.verifyNickname(
                        VerifyNickNameRequestModel (

                            firstName = firstName.value,
                            lastName = lastName.value,
                            nickName = nickName.value

                        )

                    )
                    Timber.e("fist",firstName.value)
                    _registerWithNickNameResponse.value = handleResponse(response = response)

                } catch (e: Exception){
                    e.printStackTrace()
                    _registerWithNickNameResponse.value = NetworkResult.Error(
                        context.getString(R.string.something_went_wrong)
                    )
                }
            }
        }
    }

//



}