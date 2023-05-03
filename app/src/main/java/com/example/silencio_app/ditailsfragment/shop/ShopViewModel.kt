package com.example.silencio_app.ditailsfragment.shop

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.silencio_app.R
import com.example.silencio_app.data.source.WalletDataSource
import com.example.silencio_app.ditailsfragment.wallet.resoruce.EarningRes
import com.example.silencio_app.local.UserDao
import com.example.silencio_app.util.BaseViewModel
import com.example.silencio_app.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ShopViewModel @Inject constructor(
    application: Application,
    private val walletDataSource: WalletDataSource,
    private val userDao: UserDao
): BaseViewModel(application){
    val users = userDao.getUser()

    private val _WalletShopData = MutableLiveData<NetworkResult<EarningRes>>()
    val WalletShopData: LiveData<NetworkResult<EarningRes>>
        get() = _WalletShopData



    var mContext = application

    fun getPostdatainvaidcode() {
        viewModelScope.launch {


            if (isConnected()){

                _WalletShopData.value = NetworkResult.Loading()
                try {
                    val response =  walletDataSource.getDataFromAPIWalletHstory()
                    _WalletShopData.value = handleResponse(response=response)

                }
                catch (e:java.lang.Exception)
                {
                    e.printStackTrace()
                    _WalletShopData.value = NetworkResult.Error(
                        mContext.getString(R.string.no_Internet)
                    )
                }


            }



        }
    }
}