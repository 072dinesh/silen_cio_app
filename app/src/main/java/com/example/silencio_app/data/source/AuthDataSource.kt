package com.example.silencio_app.data.source

import android.app.Application
import com.example.silencio_app.BaseResponse
import com.example.silencio_app.data.network.AuthApi
import com.example.silencio_app.ditailsfragment.auth.LoginWithPhoneReModel
import com.example.silencio_app.login.LoginWithPhoneRequestModel
import com.example.silencio_app.registrationfragment.changepassword.ChangePasswordRequestModel
import com.example.silencio_app.registrationfragment.model.*
import com.example.silencio_app.registrationfragment.response.VerifyInviteCodeReques
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject


class AuthDataSource @Inject constructor(
    private val application: Application,
    private val authApi: AuthApi,
) {

    suspend fun verifyInviteCode(verifyInviteCodeRequestModel: VerifyInviteCodeRequestModelX): Response<VerifyInviteCodeReques> {
        return authApi.verifyInviteCode(verifyInviteCodeRequestModel)

    }
    suspend fun verifyInvitePhone(verifyNumberRequestModel: VerifyNumberRequestModel): Response<BaseResponse> {
        return authApi.verifyInvitePhone(verifyNumberRequestModel)

    }
    suspend fun verifyNickname(verifyNickNameRequestModel: VerifyNickNameRequestModel): Response<BaseResponse> {
        return authApi.verifyNickname(verifyNickNameRequestModel)

    }

    suspend fun verifySignupWithPhone(registerUserRequestModel: RegisterUserRequestModel): Response<LoginWithPhoneReModel> {
        return authApi.verifySignupWithPhone(registerUserRequestModel)

    }

    suspend fun CheckPhoneNumberPassword(loginWithPhoneRequestModel: LoginWithPhoneRequestModel): Response<LoginWithPhoneReModel> {
        return authApi.CheckPhoneNumberPassword(loginWithPhoneRequestModel)

    }
    suspend fun editProfile(
        firstName: String,
        lastName: String,
        isFile: Boolean,
        imageFile: File?,
    ): Response<LoginWithPhoneReModel> {
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        val mType = "image/jpeg".toMediaTypeOrNull()
        try {
            builder.addFormDataPart("firstName", firstName)
            builder.addFormDataPart("lastName", lastName)
            builder.addFormDataPart("isFile", isFile.toString())

            if(isFile){

                if (imageFile != null && imageFile.exists()) {
                    val fileName =
                        "image${System.currentTimeMillis()}.${imageFile.extension}"
                    builder.addFormDataPart(
                        "picture",
                        fileName,
                        imageFile.asRequestBody(mType)
                    )

                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        val requestBody = builder.build()
        return authApi.editProfile(requestBody)
    }

    suspend fun changePassword(changePasswordRequestModel: ChangePasswordRequestModel): Response<LoginWithPhoneReModel>{
        return authApi.changePassword(changePasswordRequestModel)
    }

    suspend fun deleteAccount(): Response<BaseResponse> {
        return authApi.deleteAccount()
    }


}
