package com.example.silencio_app.registrationfragment.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class VerifyNickNameRequestModel (
    val firstName: String,
    val lastName: String,
    val nickName: String
)