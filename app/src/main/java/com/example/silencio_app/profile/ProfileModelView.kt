package com.example.silencio_app.profile

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.silencio_app.BaseResponse
import com.example.silencio_app.R
import com.example.silencio_app.data.source.AuthDataSource
import com.example.silencio_app.ditailsfragment.auth.LoginWithPhoneReModel
import com.example.silencio_app.ditailsfragment.auth.UserModel
import com.example.silencio_app.local.UserDao
import com.example.silencio_app.util.BaseViewModel
import com.example.silencio_app.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileModelView@Inject constructor(application: Application,
                                          private val authDataSource: AuthDataSource,
                                          private val userDao: UserDao
): BaseViewModel(application) {

    val user = userDao.getUser()

    val firstName = MutableStateFlow("")
    val lastName = MutableStateFlow("")


    private val _profileUsers = MutableLiveData<NetworkResult<LoginWithPhoneReModel>>()
    val profileUsers: LiveData<NetworkResult<LoginWithPhoneReModel>>
        get() = _profileUsers


    private val _deleteUsers = MutableLiveData<NetworkResult<BaseResponse>>()
    val deleteUsers: LiveData<NetworkResult<BaseResponse>>
        get() = _deleteUsers

    fun verifyProfileName(isFile:Boolean,imgfile:File?){
        viewModelScope.launch {
            if(isConnected()){
                _profileUsers.value = NetworkResult.Loading()
                try {
                    val response = authDataSource.editProfile(
                            firstName = firstName.value,
                            lastName = lastName.value,
                            isFile = isFile,
                            imageFile = imgfile

                    )
                    Timber.e("fist",firstName.value)
                    _profileUsers.value = handleResponse(response = response)

                } catch (e: Exception){
                    e.printStackTrace()
                    _profileUsers.value = NetworkResult.Error(
                        context.getString(R.string.something_went_wrong)
                    )
                }
            }
        }
    }

    fun deleteAccount(){
        viewModelScope.launch {
            if(isConnected()){
                _deleteUsers.value = NetworkResult.Loading()
                try {
                    val response = authDataSource.deleteAccount()

                    _deleteUsers.value = handleResponse(response = response)

                } catch (e: Exception){
                    e.printStackTrace()
                    _deleteUsers.value = NetworkResult.Error(
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