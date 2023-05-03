package com.example.silencio_app.ditailsfragment.wallet.tab

import com.example.silencio_app.BaseResponse
import com.example.silencio_app.ditailsfragment.wallet.resoruce.EarningRes
import com.example.silencio_app.ditailsfragment.wallet.resoruce.FrienListRespons
import com.example.silencio_app.ditailsfragment.wallet.resoruce.FriendList
import retrofit2.Response
import retrofit2.http.GET

interface WalletApi {
    companion object {
        const val VERIFY_INVITE_CODE = "user/wallet/getEarnings"
        //const val HOME_DESHBOARD="user/dashboard"
        const val GET_FRIND_LIST="user/wallet/getFriendList"
        const val PING_INVAIT="user/wallet/pingInactiveUsers"

    }
    @GET( VERIFY_INVITE_CODE)
    suspend fun getDataFromAPIWalletHstory():Response<EarningRes>

    @GET(GET_FRIND_LIST)
    suspend fun getDataFrindList():Response<FrienListRespons>

    @GET(PING_INVAIT)
    suspend fun invaitPingData():Response<BaseResponse>


}