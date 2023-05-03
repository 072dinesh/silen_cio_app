package com.example.silencio_app.ditailsfragment.home.respons

import com.example.silencio_app.BaseResponse
import com.google.gson.annotations.SerializedName

data class HomeDeshboardRespons (
    @SerializedName("data")
    val data:HomeDashboardModel? = null
): BaseResponse()

data class HomeDashboardModel(
    val userCount: Double? = null,
    val uploadCount: Double? = null,
    val totalHour: Double? = null,
    val noiceCoins: Double? = null,
)