package com.example.silencio_app.login

data class LoginWithPhoneRequestModel (

    val countryCode:String,
    val phone:String,
    val password:String,
    val deviceToken:String

        )