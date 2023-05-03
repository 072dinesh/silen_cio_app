package com.example.silencio_app.registrationfragment.password

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.silencio_app.R
import com.example.silencio_app.data.source.AuthDataSource
import com.example.silencio_app.ditailsfragment.auth.LoginWithPhoneReModel
import com.example.silencio_app.ditailsfragment.auth.UserModel
import com.example.silencio_app.local.UserDao
import com.example.silencio_app.registrationfragment.model.RegisterUserRequestModel
import com.example.silencio_app.util.BaseViewModel
import com.example.silencio_app.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class PasswordViewModel @Inject constructor(
    application: Application,
    private val authDataSource: AuthDataSource,
    private val userDao: UserDao

    ): BaseViewModel(application) {
    val password = MutableStateFlow("")
    val confirmpassword = MutableStateFlow("")


    val ispasswordValid = password.combine(confirmpassword) { pass, confirpass ->
        pass.isNotEmpty() && confirpass.isNotEmpty()
    }


    private val _registerSignupPhoneResponse = MutableLiveData<NetworkResult<LoginWithPhoneReModel>>()
    val registerSignupPhoneResponse: LiveData<NetworkResult<LoginWithPhoneReModel>>
        get() = _registerSignupPhoneResponse


    fun verifySignupPhone(registerUserRequestModel:RegisterUserRequestModel){
        viewModelScope.launch {
            if(isConnected()){
                _registerSignupPhoneResponse.value = NetworkResult.Loading()
                try {
                    val response = authDataSource.verifySignupWithPhone(
                        registerUserRequestModel =  registerUserRequestModel.copy (

                            password = password.value,
                            repeatPassword = confirmpassword.value,
                            registrationType = "phone"

                        )

                    )
                   // Timber.e("fist",firstName.value)
                    _registerSignupPhoneResponse.value = handleResponse(response = response)

                } catch (e: Exception){
                    e.printStackTrace()
                    _registerSignupPhoneResponse.value = NetworkResult.Error(
                        context.getString(R.string.something_went_wrong)
                    )
                }
            } else {
                _registerSignupPhoneResponse.value = NetworkResult.Error(
                    context.getString(R.string.no_Internet)
                )
            }
        }
    }

//


    suspend fun savadatauser(userModel: UserModel){
        userDao.insert(userModel)



    }


}

