package com.example.silencio_app.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.silencio_app.R
import com.example.silencio_app.data.source.AuthDataSource
import com.example.silencio_app.ditailsfragment.auth.LoginWithPhoneReModel
import com.example.silencio_app.ditailsfragment.auth.UserModel
import com.example.silencio_app.local.UserDao
import com.example.silencio_app.util.BaseViewModel
import com.example.silencio_app.util.NetworkResult
import com.example.silencio_app.util.PrefManagers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel  @Inject constructor(

    application: Application,
    private val authDataSource: AuthDataSource,
    private val userDao:UserDao
):BaseViewModel(application){
    val phoneNumber = MutableStateFlow("")
    val password = MutableStateFlow("")


    val isValid = phoneNumber.combine(password) { pn, p ->
        pn.isNotEmpty() && p.isNotEmpty()
    }


    private val _checkPhoneNumberResponse = MutableLiveData<NetworkResult<LoginWithPhoneReModel>>()
    val checkPhoneNumberResponse: LiveData<NetworkResult<LoginWithPhoneReModel>>
        get() = _checkPhoneNumberResponse


    fun checkphonenumber(CountryCode:String){
        viewModelScope.launch {
            if(isConnected()){
                _checkPhoneNumberResponse.value = NetworkResult.Loading()
                try {
                    val deviceToken = PrefManagers.getString(PrefManagers.FIRE_BASE)?:""
                    val response = authDataSource.CheckPhoneNumberPassword(
                        LoginWithPhoneRequestModel (
                        countryCode = CountryCode,
                            phone = phoneNumber.value,
                            password = password.value,
                             deviceToken = deviceToken
                        )
                    )
                    _checkPhoneNumberResponse.value = handleResponse(response = response)

                } catch (e: Exception){
                    e.printStackTrace()
                    _checkPhoneNumberResponse.value = NetworkResult.Error(
                        context.getString(R.string.something_went_wrong)
                    )
                }
            } else {
                _checkPhoneNumberResponse.value = NetworkResult.Error(
                    context.getString(R.string.no_Internet)
                )
            }
        }
    }

    suspend fun savadatauser(userModel: UserModel){
        userDao.insert(userModel)

    }

}