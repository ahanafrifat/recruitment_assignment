package com.aah.recruitmentassignment.network.retrofit

import com.aah.recruitmentassignment.models.AuthModel
import com.aah.recruitmentassignment.models.LoginModel
import com.aah.recruitmentassignment.models.UploadModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @POST("login/")
    fun requestLogin(@Body loginModel: LoginModel): Call<AuthModel>

//    @POST("v0/recruiting-entities/")
    @POST("v1/recruiting-entities/")
    fun uploadInfo(
        @Header("authorization") authorization: String,
        @Body uploadModel: UploadModel
    ): Call<UploadModel>

    @Multipart
    @POST("file-object/{FILE_TOKEN_ID}/")
    fun uploadFile(
        @Header("authorization") authorization: String,
        @Path("FILE_TOKEN_ID") fileTokenId: String,
        @Part file: MultipartBody.Part
    ): Call<UploadModel>

}