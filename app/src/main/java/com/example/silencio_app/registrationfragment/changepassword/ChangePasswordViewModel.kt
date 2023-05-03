package com.example.silencio_app.registrationfragment.changepassword

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.silencio_app.R
import com.example.silencio_app.data.source.AuthDataSource
import com.example.silencio_app.ditailsfragment.auth.LoginWithPhoneReModel
import com.example.silencio_app.ditailsfragment.auth.UserModel
import com.example.silencio_app.local.UserDao
import com.example.silencio_app.login.LoginWithPhoneRequestModel
import com.example.silencio_app.util.BaseViewModel
import com.example.silencio_app.util.NetworkResult
import com.example.silencio_app.util.PrefManagers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(application: Application,
                                                  private val authDataSource: AuthDataSource,
                                                  private val userDao: UserDao
): BaseViewModel(application) {
    val newPassword = MutableStateFlow("")
    val password = MutableStateFlow("")


    private val _changePasswordResponse = MutableLiveData<NetworkResult<LoginWithPhoneReModel>>()
    val changePasswordResponse: LiveData<NetworkResult<LoginWithPhoneReModel>>
        get() = _changePasswordResponse


    fun changePassword(){
        viewModelScope.launch {
            if(isConnected()){
                _changePasswordResponse.value = NetworkResult.Loading()
                try {
                    val deviceToken = PrefManagers.getString(PrefManagers.FIRE_BASE)?:""
                    val response = authDataSource.changePassword(
                        ChangePasswordRequestModel(
                                newPassword = newPassword.value,
                                password = password.value
                        )
                    )
                    _changePasswordResponse.value = handleResponse(response = response)

                } catch (e: Exception){
                    e.printStackTrace()
                    _changePasswordResponse.value = NetworkResult.Error(
                        context.getString(R.string.something_went_wrong)
                    )
                }
            }
        }
    }

    suspend fun savadatauser(userModel: UserModel){
        userDao.insert(userModel)

    }


}