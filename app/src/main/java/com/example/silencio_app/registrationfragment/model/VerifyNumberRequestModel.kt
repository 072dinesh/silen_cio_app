package com.example.silencio_app.registrationfragment.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VerifyNumberRequestModel (
    val countryCode:String,
    val phone:String,
    val code : String?=null
    ):Parcelable




