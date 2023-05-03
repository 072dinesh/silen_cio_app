package com.example.silencio_app.data.network

import com.example.silencio_app.BaseResponse
import com.example.silencio_app.ditailsfragment.auth.LoginWithPhoneReModel
import com.example.silencio_app.login.LoginWithPhoneRequestModel
import com.example.silencio_app.registrationfragment.changepassword.ChangePasswordRequestModel
import com.example.silencio_app.registrationfragment.model.*
import com.example.silencio_app.registrationfragment.response.VerifyInviteCodeReques
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthApi {

    companion object {
        const val VERIFY_INVITE_CODE = "user/auth/verifyInviteCode"
        const val VERIFY_PHONE = "user/auth/validatePhone"
        const val VERIFY_NICKNAME="user/auth/nickNameVerify"
        const val SIGNUP_WITH_PHONE="user/auth/registerUserWithPhone"
        const val LOGIN_USER="user/auth/loginUser"
        const val EDIT_USER_PROFILE ="user/auth/editProfile"
        const val CHANGE_PASSWORD ="user/auth/changePassword"
        const val DELETE_ACCOUNT = "user/auth/deleteAccount"
    }
    @POST(VERIFY_INVITE_CODE)
    suspend fun verifyInviteCode(
        @Body verifyInviteCodeRequestModel: VerifyInviteCodeRequestModelX
    ): Response<VerifyInviteCodeReques>

    @POST(VERIFY_PHONE)
    suspend fun verifyInvitePhone(
        @Body verifyNumberRequestModel: VerifyNumberRequestModel
    ): Response<BaseResponse>

    @POST(VERIFY_NICKNAME)
    suspend fun verifyNickname(
        @Body verifyNickNameRequestModel: VerifyNickNameRequestModel
    ): Response<BaseResponse>

    @POST(SIGNUP_WITH_PHONE)
    suspend fun verifySignupWithPhone(
        @Body registerUserRequestModel: RegisterUserRequestModel
    ): Response<LoginWithPhoneReModel>

    @POST(LOGIN_USER)
    suspend fun CheckPhoneNumberPassword(
        @Body loginWithPhoneRequestModel: LoginWithPhoneRequestModel
    ):Response<LoginWithPhoneReModel>


    @PATCH(EDIT_USER_PROFILE)
    suspend fun editProfile(
        @Body requestBody: RequestBody
    ): Response<LoginWithPhoneReModel>

    @POST(CHANGE_PASSWORD)
    suspend fun changePassword(
        @Body changePasswordRequestModel: ChangePasswordRequestModel
    ) : Response<LoginWithPhoneReModel>

    @PATCH(DELETE_ACCOUNT)
    suspend fun deleteAccount(): Response<BaseResponse>


}