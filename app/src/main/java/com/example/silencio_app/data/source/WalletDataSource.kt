package com.example.silencio_app.data.source

import android.app.Application
import com.example.silencio_app.BaseResponse
import com.example.silencio_app.ditailsfragment.wallet.resoruce.EarningRes
import com.example.silencio_app.ditailsfragment.wallet.resoruce.FrienListRespons
import com.example.silencio_app.ditailsfragment.wallet.resoruce.FriendList
import com.example.silencio_app.ditailsfragment.wallet.tab.WalletApi
import retrofit2.Response
import javax.inject.Inject

class WalletDataSource @Inject constructor(

    private val walletApihApi: WalletApi
) {

    suspend fun getDataFromAPIWalletHstory():Response<EarningRes>{
        return walletApihApi.getDataFromAPIWalletHstory()
    }

    suspend fun getRecipesFrindList(): Response<FrienListRespons>{
        return walletApihApi.getDataFrindList()
    }

    suspend fun invaitPingData(): Response<BaseResponse>{
        return walletApihApi.invaitPingData()
    }

}

