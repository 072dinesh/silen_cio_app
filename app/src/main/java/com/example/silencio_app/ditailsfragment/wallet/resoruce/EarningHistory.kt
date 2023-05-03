package com.example.silencio_app.ditailsfragment.wallet.resoruce

import com.example.silencio_app.BaseResponse

data class EarningRes(
     val data :EarningHistory? = null
):BaseResponse()

data class EarningHistory (
        val totalWalletAmount: Double?=null,
        val totalCoinsFromSamples: Double?=null,
        val totalCoinsFromReferal: Double?=null
        )