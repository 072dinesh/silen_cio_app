package com.example.silencio_app.registrationfragment.phone


import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.silencio_app.BaseResponse
import com.example.silencio_app.R
import com.example.silencio_app.data.source.AuthDataSource
import com.example.silencio_app.registrationfragment.model.VerifyNumberRequestModel
import com.example.silencio_app.util.BaseViewModel
import com.example.silencio_app.util.NetworkResult

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegiterViewModel @Inject constructor(
    private val application: Application,
    private val authDataSource: AuthDataSource,

): BaseViewModel(application) {
    val phonenumber = MutableStateFlow("")

    val isphonevail = phonenumber.map {

        it.isNotEmpty()
    }
//    private val _registerWithGoogleResponse = MutableLiveData<NetworkResult<LoginWithPhoneResponseModel>>()
//    val registerWithGoogleResponse: LiveData<NetworkResult<LoginWithPhoneResponseModel>>
//        get() = _registerWithGoogleResponse
//
//
//    fun registerWithGoogle(registerWithGoogleRequestModel: RegisterWithGoogleRequestModel){
//        viewModelScope.launch {
//            if(isConnected()){
//                _registerWithGoogleResponse.value = NetworkResult.Loading()
//                try {
//                    val response = authDataSource.registerWithGoogle(registerWithGoogleRequestModel)
//                    _registerWithGoogleResponse.value = handleResponse(response = response)
//                } catch (e: Exception){
//                    e.printStackTrace()
//                    _registerWithGoogleResponse.value = NetworkResult.Error(
//                        context.getString(R.string.something_went_wrong)
//                    )
//                }
//            } else {
//                _registerWithGoogleResponse.value = NetworkResult.Error(
//                    context.getString(R.string.no_internet)
//                )
//            }
//        }
//    }
//
//

        private val _registerWithPhoneResponse = MutableLiveData<NetworkResult<BaseResponse>>()
    val registerWithPhoneResponse: LiveData<NetworkResult<BaseResponse>>
        get() = _registerWithPhoneResponse


    fun verifynumber(countryCode:String){
        viewModelScope.launch {
            if(isConnected()){
                _registerWithPhoneResponse.value = NetworkResult.Loading()
                try {
                    val response = authDataSource.verifyInvitePhone(
                        VerifyNumberRequestModel (
                        countryCode = countryCode,
                            phone = phonenumber.value
                            )
                    )
                    _registerWithPhoneResponse.value = handleResponse(response = response)
                } catch (e: Exception){
                    e.printStackTrace()
                    _registerWithPhoneResponse.value = NetworkResult.Error(
                        context.getString(R.string.something_went_wrong)
                    )
                }
            } else {
                _registerWithPhoneResponse.value = NetworkResult.Error(
                    context.getString(R.string.no_Internet)
                )
            }
        }
    }

//



}