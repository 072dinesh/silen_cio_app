package com.example.silencio_app.registrationfragment.response

import com.example.silencio_app.BaseResponse
import com.example.silencio_app.registrationfragment.model.VerifyInviteCodeRequestModelX

import com.google.gson.annotations.SerializedName

data class VerifyInviteCodeReques(

    @SerializedName("data")
    val data : VerifyInviteCodeRequesModel? = null

    ):BaseResponse()

data class VerifyInviteCodeRequesModel(

@SerializedName("_id")
    val id:String? = null

)


