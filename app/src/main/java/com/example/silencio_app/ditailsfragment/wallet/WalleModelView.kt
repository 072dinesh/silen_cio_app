package com.example.silencio_app.ditailsfragment.wallet

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.silencio_app.BaseResponse
import com.example.silencio_app.R
import com.example.silencio_app.data.source.WalletDataSource
import com.example.silencio_app.ditailsfragment.wallet.resoruce.EarningRes
import com.example.silencio_app.ditailsfragment.wallet.resoruce.FrienListRespons
import com.example.silencio_app.local.UserDao
import com.example.silencio_app.util.BaseViewModel
import com.example.silencio_app.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalleModelView @Inject constructor(
    application: Application,
    private val walletDataSource:WalletDataSource,
    private val userDao: UserDao
    ): BaseViewModel(application)
{

    val users = userDao.getUser()

    private val _WalletData = MutableLiveData<NetworkResult<EarningRes>>()
    val WalletData: LiveData<NetworkResult<EarningRes>>
        get() = _WalletData


    private val _FrindList = MutableLiveData<NetworkResult<FrienListRespons>>()
    val FrindList: LiveData<NetworkResult<FrienListRespons>>
        get() = _FrindList

    private val _PingtData = MutableLiveData<NetworkResult<BaseResponse>>()
    val PingtData: LiveData<NetworkResult<BaseResponse>>
        get() = _PingtData

    var mContext = application

    fun getPostdatainvaidcode() {
        viewModelScope.launch {


            if (isConnected()){

                _WalletData.value = NetworkResult.Loading()
                try {
                   val response =  walletDataSource.getDataFromAPIWalletHstory()
                    _WalletData.value = handleResponse(response=response)

                }
                catch (e:java.lang.Exception)
                {
                    e.printStackTrace()
                    _WalletData.value = NetworkResult.Error(
                        mContext.getString(R.string.no_Internet)
                    )
                }
            }
        }
    }

    fun getfrindlist() {
        viewModelScope.launch {


            if (isConnected()){

                _FrindList.value = NetworkResult.Loading()
                try {
                    val response =  walletDataSource.getRecipesFrindList()
                    _FrindList.value = handleResponse(response=response)

                }
                catch (e:java.lang.Exception)
                {
                    e.printStackTrace()
                    _FrindList.value = NetworkResult.Error(
                        mContext.getString(R.string.no_Internet)
                    )
                }
            }
        }
    }

    fun getPingdata() {
        viewModelScope.launch {


            if (isConnected()){

                _PingtData.value = NetworkResult.Loading()
                try {
                    val response =  walletDataSource.invaitPingData()
                    _PingtData.value = handleResponse(response=response)

                }
                catch (e:java.lang.Exception)
                {
                    e.printStackTrace()
                    _PingtData.value = NetworkResult.Error(
                        mContext.getString(R.string.no_Internet)
                    )
                }
            }
        }
    }





}