package com.example.silencio_app.registrationfragment.model


import com.google.gson.annotations.SerializedName

data class VerifyInviteCodeRequestModelX(
//    @SerializedName("id")
//    var id: String,
    @SerializedName("inviteCode")
    var inviteCode: String,
)
