package com.lib_login

import com.lib_connect.model.Empty
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi {

    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(
        @Field("username") username: String?,
        @Field("password") password: String?,
        @Field("repassword") repassWord: String?
    ): Empty
}